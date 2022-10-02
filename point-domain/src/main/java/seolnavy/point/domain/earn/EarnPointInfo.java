package seolnavy.point.domain.earn;

import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EarnPointInfo {

	@Getter
	@AllArgsConstructor(staticName = "of")
	public static class Main {

		private Long earnPointNo; // 적립포인트번호

		private String earnUuid; // 적립_UUID

		private Long userNo; // 회원번호

		private Long earnPoint; // 적립포인트

		private Long remainPoint; // 잔여포인트

		private LocalDate expirationDate; // 만료일자

		public static Main of(final EarnPoint saveEarnPoint) {
			return Main.of(
					saveEarnPoint.getEarnPointNo(), // 적립포인트번호
					saveEarnPoint.getEarnUuid(), // 적립_UUID
					saveEarnPoint.getUserNo(), // 회원번호
					saveEarnPoint.getEarnPoint(), // 적립포인트
					saveEarnPoint.getRemainPoint(), // 잔여포인트
					saveEarnPoint.getExpirationDate() // 만료일자
			);
		}
	}

	@Getter
	@AllArgsConstructor(staticName = "of")
	public static class DeductPointResult {

		private Long earnPointNo; // 적립포인트번호

		private Long deductedPoint; // 차감된 포인트
	}

}
