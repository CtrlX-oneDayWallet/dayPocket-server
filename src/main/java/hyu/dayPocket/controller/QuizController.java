package hyu.dayPocket.controller;

import hyu.dayPocket.dto.QuizProvidingDto;
import hyu.dayPocket.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class QuizController {
    private final QuizService quizService;

    @GetMapping("/quiz-list")
    public List<QuizProvidingDto> getQuizList() {
        return quizService.provideQuiz();
    }


}
