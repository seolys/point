package seolnavy.point.interfaces.controller.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import seolnavy.point.interfaces.controller.dto.GetUserRemainPointDto;
import seolnavy.point.interfaces.controller.dto.GetUserRemainPointDto.Response;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GetUserRemainPointDtoMapper {

	public static GetUserRemainPointDto.Response of(final Long remainPoint) {
		return Response.of(remainPoint);
	}
}
