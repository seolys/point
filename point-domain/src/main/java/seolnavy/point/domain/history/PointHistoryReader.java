package seolnavy.point.domain.history;

import java.util.List;
import org.springframework.data.domain.Pageable;

public interface PointHistoryReader {

	List<PointHistory> getPointHistoryList(String userId, Pageable pageable);
	
}
