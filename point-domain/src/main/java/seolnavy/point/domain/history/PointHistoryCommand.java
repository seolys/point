package seolnavy.point.domain.history;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PointHistoryCommand {

	public static class RegisterPointHistory {

		private Long userNo; // 회원번호

		private Long earnPointNo; // 적립포인트번호

		private Long deductPointNo; // 포인트차감번호

		private PointHistoryType pointHistoryType; // 내역구분

		private Long point; // 포인트

		public PointHistory toEntity() {
			return PointHistory.create(userNo, earnPointNo, deductPointNo, pointHistoryType, point);
		}

	}

}
