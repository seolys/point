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
import seolnavy.point.interfaces.controller.dto.EarnPointDto;
import seolnavy.point.interfaces.controller.mapper.EarnPointDtoMapper;

@Slf4j
@RestController
@RequestMapping("/api/v1/points")
@RequiredArgsConstructor
public class PointCommandController {

	private final PointCommandFacade pointCommandFacade;

	@PostMapping("/{userNo}/earn")
	public CommonResponse earnPointDto(@PathVariable("userNo") final long userNo, @RequestBody final EarnPointDto.Request request) {
		final var command = EarnPointDtoMapper.of(userNo, request);
		pointCommandFacade.earnPoint(command);
		return CommonResponse.of(ResponseCode.SUCCESS);
	}

}
