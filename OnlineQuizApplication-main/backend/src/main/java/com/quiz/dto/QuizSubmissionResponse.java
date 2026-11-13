package com.quiz.dto;

import java.util.ArrayList;
import java.util.List;

public class QuizSubmissionResponse {
    private int totalQuestions;
    private int attemptedQuestions;
    private int correctAnswersCount;
    private int wrongAnswersCount;
    private double scorePercentage;
    private String performanceSummary;
    private List<QuestionResultDTO> results = new ArrayList<>();

    public QuizSubmissionResponse() {
    }

    public QuizSubmissionResponse(int totalQuestions, int attemptedQuestions, int correctAnswersCount,
                                  int wrongAnswersCount, double scorePercentage, String performanceSummary,
                                  List<QuestionResultDTO> results) {
        this.totalQuestions = totalQuestions;
        this.attemptedQuestions = attemptedQuestions;
        this.correctAnswersCount = correctAnswersCount;
        this.wrongAnswersCount = wrongAnswersCount;
        this.scorePercentage = scorePercentage;
        this.performanceSummary = performanceSummary;
        this.results = results;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public int getAttemptedQuestions() {
        return attemptedQuestions;
    }

    public void setAttemptedQuestions(int attemptedQuestions) {
        this.attemptedQuestions = attemptedQuestions;
    }

    public int getCorrectAnswersCount() {
        return correctAnswersCount;
    }

    public void setCorrectAnswersCount(int correctAnswersCount) {
        this.correctAnswersCount = correctAnswersCount;
    }

    public int getWrongAnswersCount() {
        return wrongAnswersCount;
    }

    public void setWrongAnswersCount(int wrongAnswersCount) {
        this.wrongAnswersCount = wrongAnswersCount;
    }

    public double getScorePercentage() {
        return scorePercentage;
    }

    public void setScorePercentage(double scorePercentage) {
        this.scorePercentage = scorePercentage;
    }

    public String getPerformanceSummary() {
        return performanceSummary;
    }

    public void setPerformanceSummary(String performanceSummary) {
        this.performanceSummary = performanceSummary;
    }

    public List<QuestionResultDTO> getResults() {
        return results;
    }

    public void setResults(List<QuestionResultDTO> results) {
        this.results = results;
    }
}
