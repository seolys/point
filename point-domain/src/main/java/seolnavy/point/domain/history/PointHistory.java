package seolnavy.point.domain.history;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Comment;
import seolnavy.point.domain.BaseEntity;
import seolnavy.point.domain.history.exception.UncancellableException;

@Slf4j
@Getter
@Entity
@Table(
		name = "POINT_HISTORY",
		indexes = {
				@Index(name = "INDEX_POINT_HISTORY__DEDUCT_POINT_NO", columnList = "DEDUCT_POINT_NO"),
				@Index(name = "INDEX_POINT_HISTORY__USER_NO__CREATED_DATE", columnList = "USER_NO, CREATED_DATE")
		}
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(builderMethodName = "entityBuilder", toBuilder = true)
public class PointHistory extends BaseEntity<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@Comment("포인트내역번호")
	@Column(name = "POINT_HISTORY_NO", nullable = false)
	private Long pointHistoryNo;

	@Comment("회원번호")
	@Column(name = "USER_NO", nullable = false)
	private Long userNo;

	@Comment("적립포인트번호")
	@Column(name = "EARN_POINT_NO")
	private Long earnPointNo;

	@Comment("포인트차감번호")
	@Column(name = "DEDUCT_POINT_NO")
	private Long deductPointNo;

	@Comment("내역구분")
	@Enumerated(EnumType.STRING)
	@Column(name = "HISTORY_TYPE", length = 20, nullable = false)
	private PointHistoryType historyType;

	@Comment("포인트")
	@Column(name = "POINT", nullable = false)
	private Long point;

	/**
	 * 포인트 차감 취소<br/>
	 * - 사용취소된 내역은 존재하지 내역조회에서 제외해야 한다.<br/>
	 * - 이를 위해 상태값(type)을 변경하거나, 취소여부 Flag값을 두거나, 차감취소 데이터를 INSERT 후 차감데이터와 매핑시키는 등의 전략을 취할 수 있다고 생각합니다.<br/>
	 * - 개인적으로 차감취소 INSERT를 추가 적재하는것을 선호하나, 현 프로젝트에서는 차감내역의 상태를 차감취소로 변경하는 전략을 취했습니다.<br/>
	 * - 실무 프로젝트에서는 사용취소 데이터 INSERT, 그리고 내역 조회를 QueryDSL 또는 jOOQ를 활용하여 개선할 것 같습니다.
	 */
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
