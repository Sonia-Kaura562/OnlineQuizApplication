package com.quiz;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quiz.dto.QuestionResponseDTO;
import com.quiz.dto.QuestionResultDTO;
import com.quiz.dto.QuizSubmissionRequest;
import com.quiz.dto.QuizSubmissionResponse;
import com.quiz.service.QuizService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class QuizControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private QuizService quizService;

    @Test
    void testGetQuestions_ReturnsQuestionsWithoutCorrectOption() throws Exception {
        QuestionResponseDTO q1 = new QuestionResponseDTO(1L, "What is Spring Boot?", "A", "B", "C", "D");
        when(quizService.getAllQuestions()).thenReturn(List.of(q1));

        mockMvc.perform(get("/api/quiz/questions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].questionText", is("What is Spring Boot?")))
                .andExpect(jsonPath("$[0].options.A", is("A")))
                .andExpect(jsonPath("$[0].correctOption").doesNotExist())
                .andExpect(jsonPath("$[0].explanation").doesNotExist());
    }

    @Test
    void testSubmitQuiz_ReturnsScoreAndAnswers() throws Exception {
        QuestionResultDTO r1 = new QuestionResultDTO(1L, "Question 1", "optA", "optB", "optC", "optD", "B", "B", true, "Good job");
        QuizSubmissionResponse response = new QuizSubmissionResponse(1, 1, 1, 0, 100.0, "Outstanding!", List.of(r1));

        when(quizService.submitQuiz(any(QuizSubmissionRequest.class))).thenReturn(response);

        QuizSubmissionRequest request = new QuizSubmissionRequest(Map.of(1L, "B"));

        mockMvc.perform(post("/api/quiz/submit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalQuestions", is(1)))
                .andExpect(jsonPath("$.correctAnswersCount", is(1)))
                .andExpect(jsonPath("$.scorePercentage", is(100.0)))
                .andExpect(jsonPath("$.results[0].correctOption", is("B")))
                .andExpect(jsonPath("$.results[0].isCorrect", is(true)));
    }

    @Test
    void testHealthEndpoint_ReturnsUp() throws Exception {
        mockMvc.perform(get("/api/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("UP")));
    }
}
