package seolnavy.point.domain.history;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PointHistoryCommand {

	@Getter
	@AllArgsConstructor(staticName = "of")
	public static class RegisterPointHistory {

		private Long userNo; // 회원번호

		private Long earnPointNo; // 적립포인트번호

		private Long deductPointNo; // 포인트차감번호

		private PointHistoryType pointHistoryType; // 내역구분

		private Long point; // 포인트

		public static RegisterPointHistory earnPoint(final Long userNo, final Long earnPointNo, final Long point) {
			return of(userNo, earnPointNo, null, PointHistoryType.EARN, point);
		}

		public static RegisterPointHistory deductPoint(final Long userNo, final Long deductPointNo, final Long point) {
			return of(userNo, null, deductPointNo, PointHistoryType.DEDUCT, point);
		}

		public PointHistory toEntity() {
			return PointHistory.create(userNo, earnPointNo, deductPointNo, pointHistoryType, point);
		}

	}

}
