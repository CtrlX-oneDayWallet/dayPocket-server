package hyu.dayPocket.repository;

import hyu.dayPocket.domain.FiPointHistory;
import hyu.dayPocket.domain.WithDrawalHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FiPointHistoryRepository extends JpaRepository<FiPointHistory, Long> {
}
