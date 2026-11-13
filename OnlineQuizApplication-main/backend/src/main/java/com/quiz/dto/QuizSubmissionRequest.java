package com.quiz.dto;

import java.util.HashMap;
import java.util.Map;

public class QuizSubmissionRequest {

    // Map of questionId -> selectedOption ("A", "B", "C", "D")
    private Map<Long, String> answers = new HashMap<>();

    public QuizSubmissionRequest() {
    }

    public QuizSubmissionRequest(Map<Long, String> answers) {
        this.answers = answers != null ? answers : new HashMap<>();
    }

    public Map<Long, String> getAnswers() {
        return answers;
    }

    public void setAnswers(Map<Long, String> answers) {
        this.answers = answers != null ? answers : new HashMap<>();
    }
}
