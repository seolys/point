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
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	@AllArgsConstructor(staticName = "of")
	public static class Response {

		private List<PointHistoryDetail> history;

	}

	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	@AllArgsConstructor(staticName = "of")
	public static class PointHistoryDetail {

		private Long pointHistoryNo; // 포인트내역번호
		private PointHistoryResponseType historyType; // 내역구분
		private Long point; // 포인트
		private LocalDateTime createdDate; // 등록일자
	}

	@Getter
	@AllArgsConstructor
	public enum PointHistoryResponseType {
		/** 적립 */
		EARN(PointHistoryType.EARN, "적립"),
		/** 차감 */
		DEDUCT(PointHistoryType.DEDUCT, "사용"),
		/** 차감 취소 */
		DEDUCT_CANCEL(PointHistoryType.DEDUCT_CANCEL, "사용 취소"),
		/** 만료 */
		EXPIRE(PointHistoryType.EXPIRE, "만료");

		private final PointHistoryType pointHistoryType;
		private final String description;

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
