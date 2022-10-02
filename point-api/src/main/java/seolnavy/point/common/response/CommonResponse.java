package seolnavy.point.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
public class CommonResponse<T> {

	private String code;
	private String message;
	private T data;

	public static CommonResponse of(final ResponseCode responseCode) {
		return new CommonResponse(responseCode.getCode(), responseCode.getMessage(), null);
	}

	public static CommonResponse of(final String code, final String message) {
		return new CommonResponse(code, message, null);
	}

	public static <T> CommonResponse<T> of(final T data) {
		return new CommonResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), data);
	}

}
