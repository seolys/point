package seolnavy.point.domain.deduct;

import java.util.List;
import java.util.stream.Collectors;
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

	@Getter
	@AllArgsConstructor(staticName = "of")
	public static class CancelDeductPointInfo {

		private Long deductPointNo; // 포인트차감번호

		private Long deductPoint; // 차감포인트

		List<DeductPointDetailInfo> deductPointDetailInfos; // 차감포인트 상세

		public static CancelDeductPointInfo of(final DeductPoint deductPoint, final List<DeductPointDetail> deductPointDetails) {
			final var detailInfos = deductPointDetails.stream()
					.map(DeductPointDetailInfo::of)
					.collect(Collectors.toList());
			return CancelDeductPointInfo.of(deductPoint.getDeductPointNo(), deductPoint.getDeductPoint(), detailInfos);
		}
	}

	@Getter
	@AllArgsConstructor(staticName = "of")
	public static class DeductPointDetailInfo {

		private Long earnPointNo; // 적립포인트번호

		private Long point; // 차감포인트

		public static DeductPointDetailInfo of(final DeductPointDetail deductPointDetail) {
			return DeductPointDetailInfo.of(
					deductPointDetail.getEarnPointNo(), // 적립포인트번호
					deductPointDetail.getPoint() // 차감포인트
			);
		}
	}

}
