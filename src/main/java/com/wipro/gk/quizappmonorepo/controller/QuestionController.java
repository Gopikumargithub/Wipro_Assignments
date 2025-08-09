package com.wipro.gk.quizappmonorepo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.wipro.gk.quizappmonorepo.entities.Question;
import com.wipro.gk.quizappmonorepo.enums.Category;
import com.wipro.gk.quizappmonorepo.service.QuestionService;
import com.wipro.gk.quizappmonorepo.dto.SubmitAnswersRequestDTO;
import com.wipro.gk.quizappmonorepo.exceptions.QuestionNotFoundException;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/question")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/category/{category}")
    public List<Question> getQuestionsByCategory(@PathVariable Category category) {
        List<Question> questions = questionService.getQuestionsByCategory(category);
        if (questions.isEmpty()) {
            throw new QuestionNotFoundException("No questions found for category: " + category);
        }
        return questions;
    }

    @PostMapping("/add")
    public Question addQuestion(@RequestBody Question question) {
        return questionService.addQuestion(question);
    }

    @PutMapping("/update")
    public Question updateQuestion(@RequestBody Question question) {
        return questionService.updateQuestion(question)
                .orElseThrow(() -> new QuestionNotFoundException("Question not found with ID: " + question.getId()));
    }

    @DeleteMapping("/delete/{id}")
    public String deleteQuestion(@PathVariable Long id) {
        if (!questionService.deleteQuestion(id)) {
            throw new QuestionNotFoundException("Question not found with ID: " + id);
        }
        return "Deleted question with id: " + id;
    }

    @GetMapping("/all")
    public List<Question> getAllQuestions() {
        List<Question> questions = questionService.getAllQuestions();
        if (questions.isEmpty()) {
            throw new QuestionNotFoundException("No questions found");
        }
        return questions;
    }

    @GetMapping("/{id}")
    public Question getQuestionById(@PathVariable Long id) {
        return questionService.getQuestionById(id)
                .orElseThrow(() -> new QuestionNotFoundException("Question not found with ID: " + id));
    }

    @PostMapping("/submitAnswers")
    public int submitAnswers(@RequestBody SubmitAnswersRequestDTO requestDTO) {
        return questionService.calculateScore(requestDTO);
    }
}
