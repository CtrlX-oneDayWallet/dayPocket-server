package hyu.dayPocket.repository;

import hyu.dayPocket.domain.Receipt;
import hyu.dayPocket.domain.WithDrawalHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceiptRepository extends JpaRepository<Receipt, Long> {
}
