package hyu.dayPocket.service;

import hyu.dayPocket.domain.Member;
import hyu.dayPocket.dto.MemberChosenAnswer;
import hyu.dayPocket.dto.QuizProvidingDto;
import hyu.dayPocket.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly=true)
@RequiredArgsConstructor
public class QuizService {
    private final QuizRepository quizRepository;
    private final MemberService memberService;

    public List<QuizProvidingDto> provideQuiz() {
        Long maxId = quizRepository.findMaxId();
        Set<Long> randomIds = new HashSet<>();
        Random random = new Random();

        while (randomIds.size() < 5) {
            long randomId = 1 + random.nextLong(maxId);
            randomIds.add(randomId);
        }

        return quizRepository.findAllByInIdSet(randomIds);
    }

    @Transactional
    public int checkIfCorrectAnswers(List<MemberChosenAnswer> memberChosenAnswers, Member member) {
        Set<Long> ids = memberChosenAnswers.stream()
                .map(MemberChosenAnswer::getId)
                .collect(Collectors.toSet());

        Map<Long, Boolean> correctAnswerMap = quizRepository.findAllAnswerByInIdSet(ids).stream()
                .collect(Collectors.toMap(MemberChosenAnswer::getId, MemberChosenAnswer::getAnswer));

        int correctAnswerCount = (int )memberChosenAnswers.stream()
                .filter(answer -> correctAnswerMap.containsKey(answer.getId()) &&
                        correctAnswerMap.get(answer.getId()).equals(answer.getAnswer()))
                .count();

        member.setFiPoint(member.getFiPoint() - 50 * correctAnswerCount);
        return correctAnswerCount;
    }
}
