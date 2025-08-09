package com.wipro.gk.quizappmonorepo.exceptions;

@SuppressWarnings("serial")
public class QuestionNotFoundException extends RuntimeException {
    public QuestionNotFoundException(String message) {
        super(message);
    }
}
