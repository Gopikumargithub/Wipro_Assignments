package com.wipro.gk.quizappmonorepo.dto;

import java.util.List;
import lombok.Data;

@Data
public class SubmitAnswersRequestDTO {
    private List<AnswerDTO> answers;
}
