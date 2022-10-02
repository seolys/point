package seolnavy.point.interfaces.controller.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DeductPointDto {

	@Getter
	public static class Request {

		private String uuid; // 차감 UUID

		private Long point; // 포인트
	}

	@Getter
	@AllArgsConstructor(staticName = "of")
	public static class Response {

		private Long deductPointNo; // 포인트차감번호
	}

}
