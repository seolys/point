package seolnavy.point.infra.history;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import seolnavy.point.domain.history.PointHistory;
import seolnavy.point.domain.history.PointHistoryReader;
import seolnavy.point.domain.history.PointHistoryType;

@Component
@RequiredArgsConstructor
public class PointHistoryReaderImpl implements PointHistoryReader {

	private final PointHistoryRepository pointHistoryRepository;

	@Override public Stream<PointHistory> getPointHistoryList(final Long userNo, final Pageable pageable) {
		final var pointHistoryTypes = Set.of(PointHistoryType.EARN, PointHistoryType.DEDUCT, PointHistoryType.EXPIRE);
		return pointHistoryRepository.findAllByUserNoAndHistoryTypeInOrderByCreatedDateDesc(userNo, pointHistoryTypes, pageable);
	}

	@Override public Optional<PointHistory> findByDeductPointNo(final Long deductPointNo) {
		return pointHistoryRepository.findByDeductPointNo(deductPointNo);
	}

}
