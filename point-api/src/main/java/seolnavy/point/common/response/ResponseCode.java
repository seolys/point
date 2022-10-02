package seolnavy.point.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {
	SUCCESS("0000", "Success"),

	POINTS_ALREADY_EARNED("1001", "이미 적립된 포인트입니다."),

	INVALID_PARAMETER("9001", "Invalid Parameter"),
	SYSTEM_ERROR("9999", "Internal Server Error");

	private final String code;
	private final String message;
}
