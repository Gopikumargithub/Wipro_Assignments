package com.wipro.gk.quizappmonorepo.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wipro.gk.quizappmonorepo.entities.Question;
import com.wipro.gk.quizappmonorepo.enums.Category;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByCategory(Category category);
}
