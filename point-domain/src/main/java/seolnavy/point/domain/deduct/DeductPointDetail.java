package seolnavy.point.domain.deduct;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import seolnavy.point.domain.BaseEntity;

@Getter
@Entity
@Table(name = "DEDUCT_POINT_DETAIL")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(builderMethodName = "entityBuilder", toBuilder = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class DeductPointDetail extends BaseEntity<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@Column(name = "USED_POINT_DETAIL_NO", nullable = false)
	private Long usedPointDetailNo; // 포인트사용상세번호

	@Column(name = "DEDUCT_POINT_NO", nullable = false)
	private Long deductPointNo; // 포인트차감번호

	@Column(name = "EARN_POINT_NO", nullable = false)
	private Long earnPointNo; // 적립포인트번호

	@Column(name = "POINT", nullable = false)
	private Long point; // 차감포인트

	@ManyToOne
	@JoinColumn(name = "DEDUCT_POINT_NO", insertable = false, updatable = false)
	private DeductPoint deductPoint;

	public static DeductPointDetail create(
			@NonNull final Long deductPointNo,  // 포인트차감번호
			@NonNull final Long earnPointNo,  // 적립포인트번호
			@NonNull final Long point  // 차감포인트
	) {
		return entityBuilder()
				.deductPointNo(deductPointNo) // 포인트차감번호
				.earnPointNo(earnPointNo) // 적립포인트번호
				.point(point) // 차감포인트
				.build();
	}

	@Override public Long getId() {
		return usedPointDetailNo;
	}
	
}
