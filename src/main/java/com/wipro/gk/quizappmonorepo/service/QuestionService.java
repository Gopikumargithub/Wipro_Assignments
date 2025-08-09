package com.wipro.gk.quizappmonorepo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.wipro.gk.quizappmonorepo.entities.Question;
import com.wipro.gk.quizappmonorepo.enums.Category;
import com.wipro.gk.quizappmonorepo.exceptions.QuestionNotFoundException;
import com.wipro.gk.quizappmonorepo.repos.QuestionRepository;
import com.wipro.gk.quizappmonorepo.dto.AnswerDTO;
import com.wipro.gk.quizappmonorepo.dto.SubmitAnswersRequestDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    public List<Question> getQuestionsByCategory(Category category) {
        return questionRepository.findByCategory(category);
    }

    public Question addQuestion(Question question) {
        return questionRepository.save(question);
    }

    public Optional<Question> updateQuestion(Question updatedQuestion) {
        return questionRepository.findById(updatedQuestion.getId())
                .map(existing -> questionRepository.save(updatedQuestion));
    }

    public boolean deleteQuestion(Long id) {
        if (questionRepository.existsById(id)) {
            questionRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    public Optional<Question> getQuestionById(Long id) {
        return questionRepository.findById(id);
    }

    public int calculateScore(SubmitAnswersRequestDTO requestDTO) {
        int score = 0;

        for (AnswerDTO answer : requestDTO.getAnswers()) {
            Question question = questionRepository.findById(answer.getQuestionId())
                    .orElseThrow(() -> new QuestionNotFoundException(
                            "Question not found with ID: " + answer.getQuestionId()));

            String selectedAnswerText = switch (answer.getSelectedAnswer().toLowerCase()) {
                case "option1" -> question.getOption1();
                case "option2" -> question.getOption2();
                case "option3" -> question.getOption3();
                case "option4" -> question.getOption4();
                default -> "";
            };

            if (question.getCorrectAnswer().trim().equalsIgnoreCase(selectedAnswerText.trim())) {
                score++;
            }
        }

        return score;
    }
}
