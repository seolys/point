package seolnavy.point.interfaces.controller.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import seolnavy.point.domain.deduct.DeductPointCommand;
import seolnavy.point.domain.deduct.DeductPointCommand.DeductPointRequest;
import seolnavy.point.domain.deduct.DeductPointInfo.Main;
import seolnavy.point.interfaces.controller.dto.DeductPointDto;
import seolnavy.point.interfaces.controller.dto.DeductPointDto.Response;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DeductPointDtoMapper {

	public static DeductPointCommand.DeductPointRequest of(final long userNo, final DeductPointDto.Request request) {
		return DeductPointRequest.of(
				request.getUuid(),
				userNo,
				request.getPoint()
		);
	}

	public static Response of(final Main deductPointInfo) {
		return DeductPointDto.Response.of(deductPointInfo.getDeductPointNo());
	}
}
