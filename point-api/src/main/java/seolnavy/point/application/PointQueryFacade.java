package seolnavy.point.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import seolnavy.point.domain.history.PointHistoryInfo.Main;
import seolnavy.point.domain.history.PointHistoryService;
import seolnavy.point.domain.user.UserService;

@Component
@RequiredArgsConstructor
public class PointQueryFacade {

	private final UserService userService;
	private final PointHistoryService pointHistoryService;

	public Long getUserRemainPoint(final long userNo) {
		return userService.getRemainPoint(userNo);
	}

	public List<Main> getUserPointHistory(final long userNo, final Pageable pageable) {
		return pointHistoryService.getPointHistoryList(userNo, pageable);
	}
}
