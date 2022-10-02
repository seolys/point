package seolnavy.point.interfaces.controller.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import seolnavy.point.domain.deduct.DeductPointCommand.CancelDeductPoint;
import seolnavy.point.domain.deduct.DeductPointInfo.Main;
import seolnavy.point.interfaces.controller.dto.CancelDeductPointDto.Request;
import seolnavy.point.interfaces.controller.dto.DeductPointDto.Response;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CancelDeductPointDtoMapper {

	public static CancelDeductPoint of(final long userNo, final Request request) {
		return CancelDeductPoint.of(
				userNo,
				request.getDeductPointNo()
		);
	}

	public static Response of(final Main deductPointInfo) {
		return Response.of(deductPointInfo.getDeductPointNo());
	}
}
