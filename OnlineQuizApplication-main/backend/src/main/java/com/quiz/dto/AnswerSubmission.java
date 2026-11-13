package com.quiz.dto;

import jakarta.validation.constraints.NotNull;

public class AnswerSubmission {

    @NotNull(message = "Question ID must not be null")
    private Long questionId;

    private String selectedOption; // Can be "A", "B", "C", "D" or empty if skipped

    public AnswerSubmission() {
    }

    public AnswerSubmission(Long questionId, String selectedOption) {
        this.questionId = questionId;
        this.selectedOption = selectedOption;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(String selectedOption) {
        this.selectedOption = selectedOption;
    }
}
