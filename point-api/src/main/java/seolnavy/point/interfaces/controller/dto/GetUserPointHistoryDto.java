package seolnavy.point.interfaces.controller.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import seolnavy.point.domain.history.PointHistoryType;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GetUserPointHistoryDto {

	@Getter
	@AllArgsConstructor(staticName = "of")
	public static class Response {

		private List<PointHistoryDetail> history;

	}

	@Getter
	@AllArgsConstructor(staticName = "of")
	public static class PointHistoryDetail {

		private Long pointHistoryNo; // 포인트내역번호
		private PointHistoryResponseType historyType; // 내역구분

		private Long point; // 포인트

		private LocalDateTime createdDate;
	}

	@Getter
	@AllArgsConstructor
	public enum PointHistoryResponseType {
		/** 적립 */
		EARN(PointHistoryType.EARN),
		/** 차감 */
		DEDUCT(PointHistoryType.DEDUCT),
		/** 차감 취소 */
		DEDUCT_CANCEL(PointHistoryType.DEDUCT_CANCEL),
		/** 만료 */
		EXPIRE(PointHistoryType.EXPIRE);

		private final PointHistoryType pointHistoryType;

		public static PointHistoryResponseType of(final PointHistoryType pointHistoryType) {
			if (Objects.isNull(pointHistoryType)) {
				return null;
			}
			for (final PointHistoryResponseType value : values()) {
				if (value.getPointHistoryType().equals(pointHistoryType)) {
					return value;
				}
			}
			return null;
		}
	}

}
