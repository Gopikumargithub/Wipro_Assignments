package com.wipro.gk.quizappmonorepo.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wipro.gk.quizappmonorepo.entities.Quiz;

public interface QuizRepository extends JpaRepository<Quiz, Integer> {
}
