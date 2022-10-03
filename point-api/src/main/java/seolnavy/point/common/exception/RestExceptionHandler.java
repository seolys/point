package seolnavy.point.common.exception;

import static seolnavy.point.common.response.ResponseCode.INVALID_PARAMETER;
import static seolnavy.point.common.response.ResponseCode.NOT_ENOUGH_POINTS;
import static seolnavy.point.common.response.ResponseCode.POINTS_ALREADY_EARNED;
import static seolnavy.point.common.response.ResponseCode.SYSTEM_ERROR;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import seolnavy.point.common.response.CommonResponse;
import seolnavy.point.domain.deduct.exception.NotEnoughPointsException;
import seolnavy.point.domain.deduct.exception.PointsAlreadyCancelledException;
import seolnavy.point.domain.deduct.exception.PointsAlreadyDeductedException;
import seolnavy.point.domain.earn.exception.PointsAlreadyEarnedException;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

	/**
	 * 비즈니스 로직 처리 중 에러 발생
	 */
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(CustomException.class)
	public CommonResponse<Void> handleCustomException(final CustomException e) {
		log.error(e.getMessage(), e);
		return CommonResponse.of(e.getErrorCode(), e.getErrorMessage());
	}

	/**
	 * 요청 파라미터 오류
	 */
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({IllegalArgumentException.class, BindException.class, MethodArgumentTypeMismatchException.class})
	public CommonResponse<Void> handleIllegalArgumentException(final Exception e) {
		log.error(e.getMessage(), e);
		return CommonResponse.of(INVALID_PARAMETER);
	}

	/**
	 * 시스템 에러 발생
	 */
	@ResponseBody
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public CommonResponse<Void> handleException(final Exception e) {
		log.error(e.getMessage(), e);
		return CommonResponse.of(SYSTEM_ERROR);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({PointsAlreadyEarnedException.class, PointsAlreadyCancelledException.class, PointsAlreadyDeductedException.class})
	public CommonResponse<Void> duplicateRequestException(final Exception e) {
		log.warn(e.getMessage(), e);
		return CommonResponse.of(POINTS_ALREADY_EARNED);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(NotEnoughPointsException.class)
	public CommonResponse<Void> notEnoughPointsException(final Exception e) {
		log.warn(e.getMessage(), e);
		return CommonResponse.of(NOT_ENOUGH_POINTS);
	}

}
