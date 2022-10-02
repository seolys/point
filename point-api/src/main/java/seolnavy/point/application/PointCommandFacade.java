package seolnavy.point.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import seolnavy.point.domain.earn.EarnPointCommand.RegisterPoint;
import seolnavy.point.domain.earn.EarnPointInfo;
import seolnavy.point.domain.earn.EarnPointService;
import seolnavy.point.domain.history.PointHistoryCommand.RegisterPointHistory;
import seolnavy.point.domain.history.PointHistoryService;
import seolnavy.point.domain.user.UserCommand.UpdatePoint;
import seolnavy.point.domain.user.UserService;

@Slf4j
@Component
@RequiredArgsConstructor
public class PointCommandFacade {

	private final UserService userService;
	private final EarnPointService earnPointService;
	private final PointHistoryService pointHistoryService;

	@Transactional
	public void earnPoint(final RegisterPoint command) {
		// 포인트 적립
		final EarnPointInfo.Main earnedPointInfo;
		earnedPointInfo = earnPointService.registerEarnPoint(command);

		// 히스토리 생성
		final var historyCommand = RegisterPointHistory.earnPoint(
				command.getUserNo(), earnedPointInfo.getEarnPointNo(), earnedPointInfo.getEarnPoint());
		pointHistoryService.registerEarnPoint(historyCommand);

		// 사용자 포인트 갱신
		final var updateUserPointCommand = UpdatePoint.of(command.getUserNo(), earnedPointInfo.getEarnPoint());
		userService.increaseUserPoint(updateUserPointCommand);
	}

}
