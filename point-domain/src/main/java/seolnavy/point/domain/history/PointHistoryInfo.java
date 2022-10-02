package seolnavy.point.domain.history;

import java.time.LocalDateTime;
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

		private Long point; // 포인트

		private LocalDateTime createdDate;

		public static Main of(final PointHistory pointHistory) {
			return Main.of(pointHistory.getPointHistoryNo(), pointHistory.getHistoryType(), pointHistory.getPoint(), pointHistory.getCreatedDate());
		}
	}

}
