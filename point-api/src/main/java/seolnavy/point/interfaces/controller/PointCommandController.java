package seolnavy.point.interfaces.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import seolnavy.point.application.PointCommandFacade;
import seolnavy.point.common.response.CommonResponse;
import seolnavy.point.common.response.ResponseCode;
import seolnavy.point.interfaces.controller.dto.CancelDeductPointDto;
import seolnavy.point.interfaces.controller.dto.DeductPointDto;
import seolnavy.point.interfaces.controller.dto.EarnPointDto;
import seolnavy.point.interfaces.controller.mapper.CancelDeductPointDtoMapper;
import seolnavy.point.interfaces.controller.mapper.DeductPointDtoMapper;
import seolnavy.point.interfaces.controller.mapper.EarnPointDtoMapper;

@Slf4j
@RestController
@RequestMapping("/api/v1/points")
@RequiredArgsConstructor
public class PointCommandController {

	private final PointCommandFacade pointCommandFacade;

	/**
	 * 회원별 포인트 적립
	 * @param userNo
	 * @param request
	 * @return
	 */
	@PostMapping("/{userNo}/earn")
	public CommonResponse<Void> earnPoint(@PathVariable("userNo") final long userNo, @RequestBody final EarnPointDto.Request request) {
		final var command = EarnPointDtoMapper.of(userNo, request);
		pointCommandFacade.earnPoint(command);
		return CommonResponse.of(ResponseCode.SUCCESS);
	}

	/**
	 * 회원별 포인트 사용
	 * @param userNo
	 * @param request
	 * @return
	 */
	@PostMapping("/{userNo}/deduct")
	public CommonResponse<DeductPointDto.Response> deductPoint(@PathVariable("userNo") final long userNo, @RequestBody final DeductPointDto.Request request) {
		final var command = DeductPointDtoMapper.of(userNo, request);

		final var deductPointInfo = pointCommandFacade.deductPoint(command);

		final var response = DeductPointDtoMapper.of(deductPointInfo);
		return CommonResponse.of(response);
	}

	@PostMapping("/{userNo}/cancel-deduct")
	public CommonResponse<Void> cancelDeductPoint(@PathVariable("userNo") final long userNo, @RequestBody final CancelDeductPointDto.Request request) {
		final var command = CancelDeductPointDtoMapper.of(userNo, request);
		pointCommandFacade.cancelDeductPoint(command);
		return CommonResponse.of(ResponseCode.SUCCESS);
	}

}
