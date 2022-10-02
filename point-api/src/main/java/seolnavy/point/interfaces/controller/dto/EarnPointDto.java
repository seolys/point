package seolnavy.point.interfaces.controller.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EarnPointDto {

	@Getter
	public static class Request {

		private String uuid; // 적립_UUID

		private Long point; // 포인트

	}

	@Getter
	@AllArgsConstructor
	public static class Response {

	}
}
