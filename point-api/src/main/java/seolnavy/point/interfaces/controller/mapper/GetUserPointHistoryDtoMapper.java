package seolnavy.point.interfaces.controller.mapper;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import seolnavy.point.domain.history.PointHistoryInfo.Main;
import seolnavy.point.interfaces.controller.dto.GetUserPointHistoryDto;
import seolnavy.point.interfaces.controller.dto.GetUserPointHistoryDto.PointHistoryDetail;
import seolnavy.point.interfaces.controller.dto.GetUserPointHistoryDto.PointHistoryResponseType;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GetUserPointHistoryDtoMapper {

	public static GetUserPointHistoryDto.Response of(final List<Main> userPointHistory) {
		final var pointHistoryDetails = userPointHistory.stream()
				.map(GetUserPointHistoryDtoMapper::of)
				.collect(Collectors.toList());
		return GetUserPointHistoryDto.Response.of(pointHistoryDetails);
	}

	public static PointHistoryDetail of(final Main main) {
		return PointHistoryDetail.of(
				main.getPointHistoryNo(),
				PointHistoryResponseType.of(main.getHistoryType()),
				main.getPoint(),
				main.getCreatedDate());
	}

}
