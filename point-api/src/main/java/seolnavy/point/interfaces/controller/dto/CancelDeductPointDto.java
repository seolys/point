package seolnavy.point.interfaces.controller.dto;

import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CancelDeductPointDto {

	@Getter
	public static class Request {

		@NotNull
		private Long deductPointNo; // 포인트차감번호
	}

}
