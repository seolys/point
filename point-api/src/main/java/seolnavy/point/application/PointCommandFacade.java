package seolnavy.point.application;

import javax.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import seolnavy.point.application.mapper.CancelDeductPointCommandMapper;
import seolnavy.point.application.mapper.DeductPointCommandMapper;
import seolnavy.point.application.validator.DeductPointValidator;
import seolnavy.point.domain.deduct.DeductPointCommand.CancelDeductPoint;
import seolnavy.point.domain.deduct.DeductPointCommand.DeductPointRequest;
import seolnavy.point.domain.deduct.DeductPointInfo.Main;
import seolnavy.point.domain.deduct.DeductPointService;
import seolnavy.point.domain.earn.EarnPointCommand.RegisterPoint;
import seolnavy.point.domain.earn.EarnPointInfo;
import seolnavy.point.domain.earn.EarnPointService;
import seolnavy.point.domain.history.PointHistoryCommand.RegisterPointHistory;
import seolnavy.point.domain.history.PointHistoryService;
import seolnavy.point.domain.user.UserCommand.UpdatePoint;
import seolnavy.point.domain.user.UserService;
import seolnavy.point.infra.user.UserRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class PointCommandFacade {

	private final UserService userService;
	private final UserRepository userRepository;
	private final EarnPointService earnPointService;
	private final DeductPointService deductPointService;
	private final PointHistoryService pointHistoryService;

	private final DeductPointValidator deductPointValidator;

	@Retryable(value = OptimisticLockException.class)
	@Transactional
	public EarnPointInfo.Main earnPoint(final RegisterPoint command) {
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

		return earnedPointInfo;
	}

	@Retryable(value = OptimisticLockException.class)
	@Transactional
	public Main deductPoint(final DeductPointRequest request) {
		// 유효성 체크
		deductPointValidator.validate(request);

		// 포인트 차감
		final var deductPointResults = earnPointService.deductPoints(DeductPointCommandMapper.of(request));

		// 차감내역 등록
		final var savedDeductPoint = deductPointService.registerDeductPoint(DeductPointCommandMapper.of(request, deductPointResults));
		final var deductPointNo = savedDeductPoint.getDeductPointNo(); // 포인트 차감번호

		// 히스토리 생성
		final var historyCommand = RegisterPointHistory.deductPoint(
				request.getUserNo(), deductPointNo, request.getDeductPoint());
		pointHistoryService.registerEarnPoint(historyCommand);

		// 사용자 포인트 갱신
		final var updatePointCommand = UpdatePoint.of(request.getUserNo(), request.getDeductPoint());
		userService.decreaseUserPoint(updatePointCommand);

		return savedDeductPoint;
	}

	@Retryable(value = OptimisticLockException.class)
	@Transactional
	public void cancelDeductPoint(final CancelDeductPoint command) {
		// 포인트 차감 취소
		final var cancelDeductPointInfo = deductPointService.cancelDeductPoint(command);

		// 포인트 복구
		final var restorePointCommand = CancelDeductPointCommandMapper.of(cancelDeductPointInfo);
		final var restorePointsResult = earnPointService.restorePoints(restorePointCommand);

		// 히스토리 상태 변경
		pointHistoryService.cancelDeductPoint(cancelDeductPointInfo.getDeductPointNo());

		// 사용자 포인트 갱신
		final var updatePointCommand = UpdatePoint.of(command.getUserNo(), restorePointsResult.getRestoredPoint());
		userService.increaseUserPoint(updatePointCommand);
	}

}
