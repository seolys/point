package seolnavy.point.interfaces.controller.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import seolnavy.point.domain.earn.EarnPointCommand;
import seolnavy.point.domain.earn.EarnPointCommand.RegisterPoint;
import seolnavy.point.interfaces.controller.dto.EarnPointDto.Request;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EarnPointDtoMapper {

	public static EarnPointCommand.RegisterPoint of(final long userNo, final Request request) {
		return RegisterPoint.of(
				request.getUuid(),
				userNo,
				request.getPoint()
		);
	}

}
