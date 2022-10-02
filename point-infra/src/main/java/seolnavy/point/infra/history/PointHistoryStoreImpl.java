package seolnavy.point.infra.history;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import seolnavy.point.domain.history.PointHistory;
import seolnavy.point.domain.history.PointHistoryStore;

@Component
@RequiredArgsConstructor
public class PointHistoryStoreImpl implements PointHistoryStore {

	private final PointHistoryRepository pointHistoryRepository;

	@Override public void save(final PointHistory pointHistory) {
		pointHistoryRepository.save(pointHistory);
	}

}
