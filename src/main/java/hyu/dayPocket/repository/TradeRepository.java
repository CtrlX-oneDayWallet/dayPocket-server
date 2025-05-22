package hyu.dayPocket.repository;

import hyu.dayPocket.domain.Trade;
import hyu.dayPocket.domain.WithDrawalHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradeRepository extends JpaRepository<Trade, Long> {
}
