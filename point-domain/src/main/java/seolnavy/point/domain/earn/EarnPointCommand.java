package seolnavy.point.domain.earn;

import java.util.List;
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

	@Getter
	@AllArgsConstructor(staticName = "of")
	public static class DeductPoint {

		private Long userNo; // 회원번호

		private Long point; // 차감포인트

	}

	@Getter
	@AllArgsConstructor(staticName = "of")
	public static class RestorePoint {

		List<RestorePointInfo> restorePointInfos;
	}

	@Getter
	@AllArgsConstructor(staticName = "of")
	public static class RestorePointInfo {

		private Long earnPointNo; // 적립포인트번호

		private Long deductPoint; // 차감포인트
	}

}
