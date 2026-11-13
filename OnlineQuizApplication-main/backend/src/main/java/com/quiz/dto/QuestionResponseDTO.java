package com.quiz.dto;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Public response DTO for GET /api/quiz/questions.
 * STRICT SECURITY REQUIREMENT: Never exposes correctOption or explanation!
 */
public class QuestionResponseDTO {
    private Long id;
    private String questionText;
    private Map<String, String> options = new LinkedHashMap<>();

    public QuestionResponseDTO() {
    }

    public QuestionResponseDTO(Long id, String questionText, String optionA, String optionB, String optionC, String optionD) {
        this.id = id;
        this.questionText = questionText;
        this.options.put("A", optionA);
        this.options.put("B", optionB);
        this.options.put("C", optionC);
        this.options.put("D", optionD);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
