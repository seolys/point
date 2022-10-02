package seolnavy.point.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {
	SUCCESS("0000", "Success"),
	INVALID_PARAMETER("1001", "Invalid Parameter"),
	SYSTEM_ERROR("9999", "Internal Server Error");

	private final String code;
	private final String message;
}
