package seolnavy.point.interfaces.controller.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CancelDeductPointDto {

	@Getter
	public static class Request {

		private Long deductPointNo; // 포인트차감번호
	}

}
