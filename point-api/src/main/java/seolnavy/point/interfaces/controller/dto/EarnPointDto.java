package seolnavy.point.interfaces.controller.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EarnPointDto {

	@Getter
	public static class Request {

		@NotBlank
		private String uuid; // 적립_UUID

		@NotNull
		private Long point; // 포인트

	}

}
