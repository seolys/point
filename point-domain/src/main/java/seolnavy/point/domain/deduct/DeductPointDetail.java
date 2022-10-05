package seolnavy.point.domain.deduct;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
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
import org.hibernate.annotations.Comment;
import seolnavy.point.domain.BaseEntity;

@Getter
@Entity
@Table(
		name = "DEDUCT_POINT_DETAIL",
		indexes = {
				@Index(name = "INDEX_DEDUCT_POINT_DETAIL__DEDUCT_POINT_NO", columnList = "DEDUCT_POINT_NO")
		}
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(builderMethodName = "entityBuilder", toBuilder = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class DeductPointDetail extends BaseEntity<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@Comment("포인트사용상세번호")
	@Column(name = "USED_POINT_DETAIL_NO", nullable = false)
	private Long usedPointDetailNo;

	@Comment("포인트차감번호")
	@Column(name = "DEDUCT_POINT_NO", nullable = false)
	private Long deductPointNo;

	@Comment("적립포인트번호")
	@Column(name = "EARN_POINT_NO", nullable = false)
	private Long earnPointNo;

	@Comment("차감포인트")
	@Column(name = "DEDUCT_POINT", nullable = false)
	private Long point;

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
