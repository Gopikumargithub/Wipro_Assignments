package com.wipro.gk.quizappmonorepo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.wipro.gk.quizappmonorepo.entities.QuestionWrapper;
import com.wipro.gk.quizappmonorepo.entities.Quiz;
import com.wipro.gk.quizappmonorepo.entities.Response;
import com.wipro.gk.quizappmonorepo.service.QuizService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/quiz")
public class QuizController {

    private final QuizService quizService;

    @PostMapping("/create")
    public Quiz createQuiz(
            @RequestParam String category,
            @RequestParam String level,
            @RequestParam String title) {
        return quizService.createQuiz(category, level, title);
    }

    @GetMapping("/getQuizByID/{id}")
    public List<QuestionWrapper> getQuizQuestions(@PathVariable Integer id) {
        return quizService.getQuizQuestions(id);
    }

    @PostMapping("/submitQuiz/{id}")
    public Integer submitQuiz(@PathVariable int id, @RequestBody List<Response> responses) {
        return quizService.calculateResult(id, responses);
    }
}
