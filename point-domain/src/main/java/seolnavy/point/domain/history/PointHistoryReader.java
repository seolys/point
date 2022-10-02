package seolnavy.point.domain.history;

import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.data.domain.Pageable;

public interface PointHistoryReader {

	Stream<PointHistory> getPointHistoryList(Long userId, Pageable pageable);

	Optional<PointHistory> findByDeductPointNo(Long deductPointNo);
}
