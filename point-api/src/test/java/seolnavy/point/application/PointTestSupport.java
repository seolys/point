package seolnavy.point.application;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import seolnavy.point.domain.deduct.DeductPointCommand.DeductPointRequest;
import seolnavy.point.domain.deduct.DeductPointInfo.Main;
import seolnavy.point.domain.earn.EarnPointCommand.RegisterPoint;
import seolnavy.point.domain.earn.EarnPointInfo;
import seolnavy.point.domain.user.UserService;

@Component
public class PointTestSupport {

	@Autowired private PointCommandFacade pointCommandFacade;
	@Autowired private UserService userService;

	public EarnPointInfo.Main earnPoint(final Long userNo, final Long earnPoint) {
		final var earnUuid = UUID.randomUUID().toString();
		final var command = RegisterPoint.of(earnUuid, userNo, earnPoint);
		return pointCommandFacade.earnPoint(command);
	}

	public Main deductPoint(final Long userNo, final Long deductPoint) {
		final var deductUuid = UUID.randomUUID().toString();
		final var command = DeductPointRequest.of(deductUuid, userNo, deductPoint);
		return pointCommandFacade.deductPoint(command);
	}

	public Long findUserRemainPoint(final long userNo) {
		return userService.getRemainPoint(userNo);
	}

}
