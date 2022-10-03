package seolnavy.point.application;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import seolnavy.point.domain.deduct.DeductPointCommand.DeductPointRequest;
import seolnavy.point.domain.deduct.DeductPointInfo.Main;
import seolnavy.point.domain.earn.EarnPointCommand.RegisterPoint;
import seolnavy.point.domain.earn.EarnPointInfo;

@Component
public class PointCommandTestSupport {

	@Autowired private PointCommandFacade pointCommandFacade;

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

}
