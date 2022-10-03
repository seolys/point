package seolnavy.point.domain.earn;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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

		public List<Long> getEarnPointNos() {
			return restorePointInfos.stream()
					.map(RestorePointInfo::getEarnPointNo)
					.collect(Collectors.toList());
		}

		public Map<Long, Long> getDeductPointMapByEarnPointNo() {
			return restorePointInfos.stream()
					.collect(Collectors.toMap(RestorePointInfo::getEarnPointNo, RestorePointInfo::getDeductPoint));
		}
	}

	@Getter
	@AllArgsConstructor(staticName = "of")
	public static class RestorePointInfo {

		private Long earnPointNo; // 적립포인트번호

		private Long deductPoint; // 차감포인트
	}

}
