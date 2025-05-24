package hyu.dayPocket.controller;

import hyu.dayPocket.config.CustomUserDetails;
import hyu.dayPocket.dto.MemberChosenAnswer;
import hyu.dayPocket.dto.QuizProvidingDto;
import hyu.dayPocket.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping("/submit/quiz")
    public boolean[] checkClientAnswer(@RequestBody List<MemberChosenAnswer> dto, @AuthenticationPrincipal CustomUserDetails userDetails) {
        return quizService.checkIfCorrectAnswers(dto, userDetails.getMember());
    }
}
