package hyu.dayPocket.repository;

import hyu.dayPocket.domain.FiPointHistory;
import hyu.dayPocket.domain.WithDrawalHistory;
import hyu.dayPocket.enums.ChallengeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FiPointHistoryRepository extends JpaRepository<FiPointHistory, Long> {

    Optional<FiPointHistory> findTopByMemberIdAndChallengeOrderByDateDesc(Long memberId, ChallengeType type);
}
