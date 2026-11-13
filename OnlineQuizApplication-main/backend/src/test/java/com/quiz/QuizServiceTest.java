package com.quiz;

import com.quiz.dto.QuestionResponseDTO;
import com.quiz.dto.QuizSubmissionRequest;
import com.quiz.dto.QuizSubmissionResponse;
import com.quiz.entity.Question;
import com.quiz.exception.QuizValidationException;
import com.quiz.repository.QuestionRepository;
import com.quiz.service.QuizService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QuizServiceTest {

    @Mock
    private QuestionRepository questionRepository;

    @InjectMocks
    private QuizService quizService;

    private List<Question> sampleQuestions;

    @BeforeEach
    void setUp() {
        Question q1 = new Question("What is 2+2?", "3", "4", "5", "6", "B", "2+2 equals 4");
        q1.setId(1L);

        Question q2 = new Question("Capital of France?", "London", "Berlin", "Paris", "Madrid", "C", "Paris is the capital");
        q2.setId(2L);

        sampleQuestions = List.of(q1, q2);
    }

    @Test
    void testGetAllQuestions_NeverExposesCorrectOption() {
        when(questionRepository.findAllByOrderByIdAsc()).thenReturn(sampleQuestions);

        List<QuestionResponseDTO> questions = quizService.getAllQuestions();

        assertEquals(2, questions.size());
        assertEquals("What is 2+2?", questions.get(0).getQuestionText());
        assertEquals("4", questions.get(0).getOptions().get("B"));
        // QuestionResponseDTO class does not even have getCorrectOption method,
        // ensuring answers can never be leaked to the frontend before submission!
    }

    @Test
    void testSubmitQuiz_CalculatesAccurateScore() {
        when(questionRepository.findAllByOrderByIdAsc()).thenReturn(sampleQuestions);

        QuizSubmissionRequest request = new QuizSubmissionRequest();
        request.setAnswers(Map.of(
                1L, "B", // Correct
                2L, "A"  // Incorrect (correct is C)
        ));

        QuizSubmissionResponse response = quizService.submitQuiz(request);

        assertNotNull(response);
        assertEquals(2, response.getTotalQuestions());
        assertEquals(2, response.getAttemptedQuestions());
        assertEquals(1, response.getCorrectAnswersCount());
        assertEquals(1, response.getWrongAnswersCount());
        assertEquals(50.0, response.getScorePercentage());

        // Verify that after submission, correct answers and explanations ARE revealed
        assertEquals("B", response.getResults().get(0).getCorrectOption());
        assertTrue(response.getResults().get(0).isCorrect());

        assertEquals("C", response.getResults().get(1).getCorrectOption());
        assertEquals("A", response.getResults().get(1).getSelectedOption());
        assertFalse(response.getResults().get(1).isCorrect());
    }

    @Test
    void testSubmitQuiz_InvalidOptionThrowsException() {
        when(questionRepository.findAllByOrderByIdAsc()).thenReturn(sampleQuestions);

        QuizSubmissionRequest request = new QuizSubmissionRequest();
        request.setAnswers(Map.of(1L, "INVALID_OPTION"));

        assertThrows(QuizValidationException.class, () -> quizService.submitQuiz(request));
    }
}
