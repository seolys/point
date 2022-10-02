package seolnavy.point.infra.history;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import seolnavy.point.domain.history.PointHistory;
import seolnavy.point.domain.history.PointHistoryReader;

@Component
public class PointHistoryReaderImpl implements PointHistoryReader {

	@Override public List<PointHistory> getPointHistoryList(final String userId, final Pageable pageable) {
		return null;
	}
	
}
