package seolnavy.point.interfaces.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import seolnavy.point.application.PointQueryFacade;
import seolnavy.point.common.response.CommonResponse;
import seolnavy.point.interfaces.controller.dto.GetUserPointHistoryDto;
import seolnavy.point.interfaces.controller.dto.GetUserRemainPointDto;
import seolnavy.point.interfaces.controller.mapper.GetUserPointHistoryDtoMapper;
import seolnavy.point.interfaces.controller.mapper.GetUserRemainPointDtoMapper;

@RestController
@RequestMapping("/api/v1/points")
@RequiredArgsConstructor
public class PointQueryController {

	private final PointQueryFacade pointQueryFacade;

	/**
	 * 회원별 포인트 조회
	 * @param userNo
	 * @return
	 */
	@GetMapping("/{userNo}")
	public CommonResponse<GetUserRemainPointDto.Response> getUserRemainPoint(@PathVariable("userNo") final long userNo) {
		final var remainPoint = pointQueryFacade.getUserRemainPoint(userNo);
		final var response = GetUserRemainPointDtoMapper.of(remainPoint);
		return CommonResponse.of(response);
	}

	/**
	 * 회원별 포인트 적립/사용내역 조회
	 * @param userNo
	 * @param pageable
	 * @return
	 */
	@GetMapping("/{userNo}/history")
	public CommonResponse<GetUserPointHistoryDto.Response> getUserPointHistory(
			@PathVariable("userNo") final long userNo,
			@PageableDefault final Pageable pageable
	) {
		final var userPointHistory = pointQueryFacade.getUserPointHistory(userNo, pageable);
		final var response = GetUserPointHistoryDtoMapper.of(userPointHistory);
		return CommonResponse.of(response);
	}

}
