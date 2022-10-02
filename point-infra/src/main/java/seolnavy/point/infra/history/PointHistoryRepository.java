package seolnavy.point.infra.history;

import java.util.Collection;
import java.util.stream.Stream;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import seolnavy.point.domain.history.PointHistory;
import seolnavy.point.domain.history.PointHistoryType;

public interface PointHistoryRepository extends JpaRepository<PointHistory, Long> {

	Stream<PointHistory> findAllByUserNoAndHistoryTypeInOrderByCreatedDateDesc(Long userNo, Collection<PointHistoryType> pointHistoryTypes, Pageable pageable);

}
