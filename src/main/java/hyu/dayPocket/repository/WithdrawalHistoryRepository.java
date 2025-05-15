package hyu.dayPocket.repository;

import hyu.dayPocket.domain.WithDrawalHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WithdrawalHistoryRepository extends JpaRepository<WithDrawalHistory, Long> {
}
