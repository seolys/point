package seolnavy.point.domain.history;


import java.util.List;
import org.springframework.data.domain.Pageable;

public interface PointHistoryService {

	void registerEarnPoint(PointHistoryCommand.RegisterPointHistory request);

	/**
	 * 회원별 포인트 적립/사용내역 조회
	 * @param userId
	 * @param pageable
	 * @return
	 */
	List<PointHistoryInfo.Main> getPointHistoryList(Long userId, Pageable pageable);

}
