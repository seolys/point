package seolnavy.point.domain.deduct;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DeductPointInfo {

	@Getter
	@AllArgsConstructor(staticName = "of")
	public static class Main {

		private Long deductPointNo; // 포인트차감번호

	}

}
