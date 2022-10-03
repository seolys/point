package seolnavy.point.application.validator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import seolnavy.point.domain.deduct.DeductPointCommand.DeductPointRequest;
import seolnavy.point.domain.deduct.DeductPointService;
import seolnavy.point.domain.deduct.exception.NotEnoughPointsException;
import seolnavy.point.domain.deduct.exception.PointsAlreadyDeductedException;
import seolnavy.point.domain.user.UserService;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeductPointValidator {

	private final DeductPointService deductPointService;
	private final UserService userService;

	public void validate(final DeductPointRequest command) {
		// uuid로 이미 등록된 차감인지 확인
		final var exists = deductPointService.existsByDeductUuid(command.getDeductUuid());
		if (exists) {
			log.warn("이미 등록된 차감입니다. deductUuid={}", command.getDeductUuid());
			throw new PointsAlreadyDeductedException(command.getDeductUuid());
		}

		// 잔액 확인
		final var remainPoint = userService.getRemainPoint(command.getUserNo());
		if (remainPoint - command.getDeductPoint() < 0) {
			log.warn("가용 포인트가 부족합니다. userNo={}, remainPoint={}, deductPoint={}", command.getUserNo(), remainPoint, command.getDeductPoint());
			throw new NotEnoughPointsException(command.getUserNo(), remainPoint, command.getDeductPoint());
		}
	}
}
