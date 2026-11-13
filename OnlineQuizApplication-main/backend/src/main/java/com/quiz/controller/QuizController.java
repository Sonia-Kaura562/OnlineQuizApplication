package com.quiz.controller;

import com.quiz.dto.QuestionResponseDTO;
import com.quiz.dto.QuizSubmissionRequest;
import com.quiz.dto.QuizSubmissionResponse;
import com.quiz.service.QuizService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    /**
     * GET /api/quiz/questions
     * Returns list of questions with their options.
     * Note: Does not expose correct answers or explanations!
     */
    @GetMapping("/questions")
    public ResponseEntity<List<QuestionResponseDTO>> getQuestions() {
        List<QuestionResponseDTO> questions = quizService.getAllQuestions();
        return ResponseEntity.ok(questions);
    }

    /**
     * POST /api/quiz/submit
     * Accepts answers map, computes score and returns results with correct answers.
     */
    @PostMapping("/submit")
    public ResponseEntity<QuizSubmissionResponse> submitQuiz(@Valid @RequestBody QuizSubmissionRequest request) {
        QuizSubmissionResponse response = quizService.submitQuiz(request);
        return ResponseEntity.ok(response);
    }
}
