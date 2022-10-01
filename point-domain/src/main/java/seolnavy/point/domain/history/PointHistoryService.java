package seolnavy.point.domain.history;


import java.util.List;
import org.springframework.data.domain.Pageable;

public interface PointHistoryService {

	void registerEarnPoint(PointHistoryCommand.RegisterPointHistory request);

	List<PointHistoryInfo.Main> getPointHistoryList(String userId, Pageable pageable);

}
