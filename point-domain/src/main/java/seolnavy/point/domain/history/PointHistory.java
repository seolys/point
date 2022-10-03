package seolnavy.point.domain.history;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import seolnavy.point.domain.BaseEntity;
import seolnavy.point.domain.history.exception.UncancellableException;

@Slf4j
@Getter
@Entity
@Table(name = "POINT_HISTORY")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(builderMethodName = "entityBuilder", toBuilder = true)
public class PointHistory extends BaseEntity<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@Column(name = "POINT_HISTORY_NO", nullable = false)
	private Long pointHistoryNo; // 포인트내역번호

	@Column(name = "USER_NO", nullable = false)
	private Long userNo; // 회원번호

	@Column(name = "EARN_POINT_NO")
	private Long earnPointNo; // 적립포인트번호

	@Column(name = "DEDUCT_POINT_NO")
	private Long deductPointNo; // 포인트차감번호

	@Column(name = "HISTORY_TYPE", length = 20, nullable = false) @Enumerated(EnumType.STRING)
	private PointHistoryType historyType; // 내역구분

	@Column(name = "POINT", nullable = false)
	private Long point; // 포인트

	public void deductCancel() {
		if (PointHistoryType.DEDUCT != historyType) {
			log.error("포인트 차감 내역만 취소가 가능합니다. pointHistoryNo={}, historyType={}", pointHistoryNo, historyType);
			throw new UncancellableException();
		}
		historyType = PointHistoryType.DEDUCT_CANCEL;
	}

	public static PointHistory create(
			@NonNull final Long userNo,  // 회원번호
			final Long earnPointNo,  // 적립포인트번호
			final Long deductPointNo,  // 포인트차감번호
			@NonNull final PointHistoryType historyType,  // 내역구분
			@NonNull final Long point  // 포인트
	) {
		return entityBuilder()
				.userNo(userNo) // 회원번호
				.earnPointNo(earnPointNo) // 적립포인트번호
				.deductPointNo(deductPointNo) // 포인트차감번호
				.historyType(historyType) // 내역구분
				.point(point) // 포인트
				.build();
	}

	@Override public Long getId() {
		return pointHistoryNo;
	}
}