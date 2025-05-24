package hyu.dayPocket.service;

import hyu.dayPocket.domain.FiPointHistory;
import hyu.dayPocket.domain.Member;
import hyu.dayPocket.dto.MemberChosenAnswer;
import hyu.dayPocket.dto.QuizProvidingDto;
import hyu.dayPocket.enums.ChallengeType;
import hyu.dayPocket.enums.PointPaymentState;
import hyu.dayPocket.repository.FiPointHistoryRepository;
import hyu.dayPocket.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly=true)
@RequiredArgsConstructor
public class QuizService {
    private final QuizRepository quizRepository;
    private final MemberService memberService;
    private final FiPointHistoryRepository fiPointHistoryRepository;

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
    public boolean[] checkIfCorrectAnswers(List<MemberChosenAnswer> memberChosenAnswers, Member member) {
        Set<Long> ids = memberChosenAnswers.stream()
                .map(MemberChosenAnswer::getId)
                .collect(Collectors.toSet());

        Map<Long, Boolean> correctAnswerMap = quizRepository.findAllAnswerByInIdSet(ids).stream()
                .collect(Collectors.toMap(MemberChosenAnswer::getId, MemberChosenAnswer::getAnswer));

        int cnt = 0;
        int correctAnswerCnt = 0;
        boolean[] quizAnswers = new boolean[5];

        for (MemberChosenAnswer memberChosenAnswer : memberChosenAnswers) {
            if (correctAnswerMap.containsKey(memberChosenAnswer.getId()) &&
                    correctAnswerMap.get(memberChosenAnswer.getId()).equals(memberChosenAnswer.getAnswer())) {
                quizAnswers[cnt] = true;
                correctAnswerCnt++;
            }
            cnt++;
        }

        member.setFiPoint(member.getFiPoint() + 50 * correctAnswerCnt);
        member.setFiScore(member.getFiScore() + 50L * correctAnswerCnt);
        FiPointHistory fiPointHistory = FiPointHistory.fiPointHistoryFrom(member, 50 * correctAnswerCnt, PointPaymentState.APPROVED,  ChallengeType.QUIZ ,LocalDateTime.now());
        fiPointHistoryRepository.save(fiPointHistory);
        return quizAnswers;
    }
}
