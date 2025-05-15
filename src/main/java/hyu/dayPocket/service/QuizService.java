package hyu.dayPocket.service;

import hyu.dayPocket.dto.QuizProvidingDto;
import hyu.dayPocket.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Service
@Transactional(readOnly=true)
@RequiredArgsConstructor
public class QuizService {
    private final QuizRepository quizRepository;

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
}
