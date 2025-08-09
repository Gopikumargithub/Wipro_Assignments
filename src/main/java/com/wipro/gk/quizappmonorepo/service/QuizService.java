package com.wipro.gk.quizappmonorepo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.wipro.gk.quizappmonorepo.entities.Question;
import com.wipro.gk.quizappmonorepo.entities.QuestionWrapper;
import com.wipro.gk.quizappmonorepo.entities.Quiz;
import com.wipro.gk.quizappmonorepo.entities.Response;
import com.wipro.gk.quizappmonorepo.enums.Category;
import com.wipro.gk.quizappmonorepo.enums.DifficultyLevel;
import com.wipro.gk.quizappmonorepo.repos.QuestionRepository;
import com.wipro.gk.quizappmonorepo.repos.QuizRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;

    public Quiz createQuiz(String category, String level, String title) {
        Category enumCategory = Category.valueOf(category.toUpperCase());
        DifficultyLevel enumLevel = DifficultyLevel.valueOf(level.toUpperCase());
        List<Question> questions = questionRepository.findByCategory(enumCategory);
        List<Question> filteredQuestions = new ArrayList<>();

        for (Question q : questions) {
            if (q.getDifficultyLevel() == enumLevel) {
                filteredQuestions.add(q);
            }
        }

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(filteredQuestions);
        return quizRepository.save(quiz);
    }

    public List<QuestionWrapper> getQuizQuestions(Integer id) {
        Quiz quiz = quizRepository.findById(id).orElseThrow();
        List<Question> questions = quiz.getQuestions();
        List<QuestionWrapper> wrappers = new ArrayList<>();

        for (Question q : questions) {
            QuestionWrapper wrapper = new QuestionWrapper();
            wrapper.setQuestionTitle(q.getQuestionTitle());
            wrapper.setOption1(q.getOption1());
            wrapper.setOption2(q.getOption2());
            wrapper.setOption3(q.getOption3());
            wrapper.setOption4(q.getOption4());
            wrappers.add(wrapper);
        }

        return wrappers;
    }

    public Integer calculateResult(Integer id, List<Response> responses) {
        Quiz quiz = quizRepository.findById(id).orElseThrow();
        List<Question> questions = quiz.getQuestions();
        int score = 0;

        for (int i = 0; i < responses.size(); i++) {
            if (responses.get(i).getAnswer().equals(questions.get(i).getCorrectAnswer())) {
                score++;
            }
        }

        return score;
    }
}
