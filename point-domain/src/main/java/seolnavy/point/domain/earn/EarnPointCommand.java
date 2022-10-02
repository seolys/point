package seolnavy.point.domain.earn;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EarnPointCommand {

	@Getter
	@AllArgsConstructor(staticName = "of")
	public static class RegisterPoint {

		private String earnUuid; // 적립_UUID

		private Long userNo; // 회원번호

		private Long earnPoint; // 적립포인트

		public EarnPoint toEntity() {
			return EarnPoint.create(earnUuid, userNo, earnPoint);
		}

	}

}
