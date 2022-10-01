package seolnavy.point.domain.deduct;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DeductPointCommand {

	@Getter
	@AllArgsConstructor(staticName = "of")
	public static class RegisterDeductPoint {

		private String deductUuid; // 차감_UUID

		private Long userNo; // 회원번호

		private Long deductPoint; // 차감포인트

	}

	@Getter
	@AllArgsConstructor(staticName = "of")
	public static class CancelDeductPoint {

		private Long deductPointNo; // 포인트차감번호
		
	}

}
