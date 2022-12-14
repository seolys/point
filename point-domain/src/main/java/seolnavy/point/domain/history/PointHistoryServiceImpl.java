package seolnavy.point.domain.history;

import static java.util.stream.Collectors.toList;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seolnavy.point.domain.history.PointHistoryCommand.RegisterPointHistory;
import seolnavy.point.domain.history.PointHistoryInfo.Main;
import seolnavy.point.domain.history.exception.PointHistoryNotFound;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PointHistoryServiceImpl implements PointHistoryService {

	private final PointHistoryReader pointHistoryReader;
	private final PointHistoryStore pointHistoryStore;

	@Transactional
	@Override public void registerEarnPoint(final RegisterPointHistory request) {
		pointHistoryStore.save(request.toEntity());
	}

	@Override public List<Main> getPointHistoryList(final Long userNo, final Pageable pageable) {
		final var pointHistoryList = pointHistoryReader.getPointHistoryList(userNo, pageable);
		return pointHistoryList
				.map(PointHistoryInfo.Main::of)
				.collect(toList());
	}

	@Override public void cancelDeductPoint(final Long deductPointNo) {
		final var pointHistory = pointHistoryReader.findByDeductPointNo(deductPointNo)
				.orElseThrow(PointHistoryNotFound::new);
		pointHistory.deductCancel();
	}

}
