package com.quiz.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.LinkedHashMap;
import java.util.Map;

public class QuestionResultDTO {
    private Long questionId;
    private String questionText;
    private Map<String, String> options = new LinkedHashMap<>();
    private String selectedOption;
    private String correctOption;

    @JsonProperty("isCorrect")
    private boolean isCorrect;
    private String explanation;

    public QuestionResultDTO() {
    }

    public QuestionResultDTO(Long questionId, String questionText, String optionA, String optionB, 
                             String optionC, String optionD, String selectedOption, 
                             String correctOption, boolean isCorrect, String explanation) {
        this.questionId = questionId;
        this.questionText = questionText;
        this.options.put("A", optionA);
        this.options.put("B", optionB);
        this.options.put("C", optionC);
        this.options.put("D", optionD);
        this.selectedOption = selectedOption;
        this.correctOption = correctOption;
        this.isCorrect = isCorrect;
        this.explanation = explanation;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public Map<String, String> getOptions() {
        return options;
    }

    public void setOptions(Map<String, String> options) {
        this.options = options;
    }

    public String getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(String selectedOption) {
        this.selectedOption = selectedOption;
    }

    public String getCorrectOption() {
        return correctOption;
    }

    public void setCorrectOption(String correctOption) {
        this.correctOption = correctOption;
    }

    @JsonProperty("isCorrect")
    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }
}
