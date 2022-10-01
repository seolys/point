package seolnavy.point.domain.history;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PointHistoryInfo {

	@Getter
	@AllArgsConstructor(staticName = "of")
	public static class Main {

		private Long pointHistoryNo; // 포인트내역번호

		private PointHistoryType historyType; // 내역구분

		private String point; // 포인트

		public static Main of(final PointHistory pointHistory) {
			return Main.of(pointHistory.getPointHistoryNo(), pointHistory.getHistoryType(), pointHistory.getPoint());
		}
	}

}
