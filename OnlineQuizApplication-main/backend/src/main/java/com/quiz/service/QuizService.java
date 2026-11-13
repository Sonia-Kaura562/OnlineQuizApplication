package com.quiz.service;

import com.quiz.dto.QuestionResponseDTO;
import com.quiz.dto.QuestionResultDTO;
import com.quiz.dto.QuizSubmissionRequest;
import com.quiz.dto.QuizSubmissionResponse;
import com.quiz.entity.Question;
import com.quiz.exception.QuizValidationException;
import com.quiz.repository.QuestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class QuizService {

    private final QuestionRepository questionRepository;

    public QuizService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    /**
     * Retrieves all questions without revealing correct answers or explanations.
     */
    @Transactional(readOnly = true)
    public List<QuestionResponseDTO> getAllQuestions() {
        List<Question> questions = questionRepository.findAllByOrderByIdAsc();
        return questions.stream()
                .map(q -> new QuestionResponseDTO(
                        q.getId(),
                        q.getQuestionText(),
                        q.getOptionA(),
                        q.getOptionB(),
                        q.getOptionC(),
                        q.getOptionD()
                ))
                .collect(Collectors.toList());
    }

    /**
     * Evaluates quiz submission, computes score & percentage, and reveals correct answers and explanations.
     */
    @Transactional(readOnly = true)
    public QuizSubmissionResponse submitQuiz(QuizSubmissionRequest submissionRequest) {
        List<Question> questions = questionRepository.findAllByOrderByIdAsc();

        if (questions.isEmpty()) {
            throw new QuizValidationException("No questions available in the quiz database");
        }

        Map<Long, String> userAnswers = submissionRequest != null && submissionRequest.getAnswers() != null
                ? submissionRequest.getAnswers()
                : Map.of();

        int totalQuestions = questions.size();
        int attemptedCount = 0;
        int correctCount = 0;
        List<QuestionResultDTO> results = new ArrayList<>();

        for (Question question : questions) {
            String selectedOption = userAnswers.get(question.getId());
            boolean hasAttempted = selectedOption != null && !selectedOption.trim().isEmpty();

            String sanitizedSelected = null;
            boolean isCorrect = false;

            if (hasAttempted) {
                sanitizedSelected = selectedOption.trim().toUpperCase();
                // Validate option key
                if (!List.of("A", "B", "C", "D").contains(sanitizedSelected)) {
                    throw new QuizValidationException("Invalid option selected for question ID " + question.getId() + ": " + selectedOption);
                }
                attemptedCount++;
                if (sanitizedSelected.equalsIgnoreCase(question.getCorrectOption().trim())) {
                    isCorrect = true;
                    correctCount++;
                }
            }

            results.add(new QuestionResultDTO(
                    question.getId(),
                    question.getQuestionText(),
                    question.getOptionA(),
                    question.getOptionB(),
                    question.getOptionC(),
                    question.getOptionD(),
                    sanitizedSelected,
                    question.getCorrectOption().trim().toUpperCase(),
                    isCorrect,
                    question.getExplanation()
            ));
        }

        int wrongCount = attemptedCount - correctCount;
        double percentage = totalQuestions > 0
                ? Math.round((correctCount * 100.0 / totalQuestions) * 10.0) / 10.0
                : 0.0;

        String summary = generateSummary(percentage);

        return new QuizSubmissionResponse(
                totalQuestions,
                attemptedCount,
                correctCount,
                wrongCount,
                percentage,
                summary,
                results
        );
    }

    private String generateSummary(double percentage) {
        if (percentage >= 100.0) {
            return "Outstanding! Perfect score!";
        } else if (percentage >= 80.0) {
            return "Great job! You have demonstrated strong subject knowledge.";
        } else if (percentage >= 60.0) {
            return "Good effort! Review the explanations for questions you missed.";
        } else {
            return "Needs improvement. Review the concepts and retake the quiz.";
        }
    }
}
