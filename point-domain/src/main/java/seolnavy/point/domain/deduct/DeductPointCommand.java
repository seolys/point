package seolnavy.point.domain.deduct;

import static java.util.stream.Collectors.toList;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DeductPointCommand {

	@Getter
	@AllArgsConstructor(staticName = "of")
	public static class DeductPointRequest {

		private String deductUuid; // 차감_UUID

		private Long userNo; // 회원번호

		private Long deductPoint; // 차감포인트

	}

	@Getter
	@AllArgsConstructor(staticName = "of")
	public static class RegisterDeductPoint {

		private String deductUuid; // 차감_UUID

		private Long userNo; // 회원번호

		private Long deductPoint; // 차감포인트

		private List<RegisterDeductPointDetail> registerDeductPointDetailList;

		public DeductPoint toEntity() {
			return DeductPoint.create(deductUuid, userNo, deductPoint);
		}

		public List<DeductPointDetail> toEntityDetails(final Long deductPointNo) {
			return registerDeductPointDetailList.stream()
					.map(registerDeductPointDetail -> registerDeductPointDetail.toEntity(deductPointNo))
					.collect(toList());
		}

	}

	@Getter
	@AllArgsConstructor(staticName = "of")
	public static class RegisterDeductPointDetail {

		private Long earnPointNo; // 적립포인트번호

		private Long deductedPoint; // 차감된 포인트

		public DeductPointDetail toEntity(final Long deductPointNo) {
			return DeductPointDetail.create(deductPointNo, earnPointNo, deductedPoint);
		}

	}


	@Getter
	@AllArgsConstructor(staticName = "of")
	public static class CancelDeductPoint {

		private Long deductPointNo; // 포인트차감번호

	}

}
