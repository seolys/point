package seolnavy.point.domain.deduct;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import seolnavy.point.domain.BaseEntity;

/**
 * 포인트 차감
 */
@Getter
@Entity
@Table(
		name = "DEDUCT_POINT",
		uniqueConstraints = @UniqueConstraint(name = "UNI_DEDUCT_POINT_UUID", columnNames = {"DEDUCT_UUID"})
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(builderMethodName = "entityBuilder", toBuilder = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class DeductPoint extends BaseEntity<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@Column(name = "DEDUCT_POINT_NO", nullable = false)
	private Long deductPointNo; // 포인트차감번호

	@Column(name = "DEDUCT_UUID", nullable = false)
	private String deductUuid; // 차감_UUID

	@Column(name = "USER_NO", nullable = false)
	private Long userNo; // 회원번호

	@Column(name = "DEDUCT_POINT", nullable = false)
	private Long deductPoint; // 차감포인트

	@Column(name = "DEDUCT_STATUS", length = 10, nullable = false) @Enumerated(EnumType.STRING)
	private DeductPointStatus deductStatus; // 포인트 사용 상태

	@Version
	@Column(name = "VERSION", nullable = false)
	private Long version; // 버전

	@Default
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "deductPoint", cascade = CascadeType.PERSIST)
	private List<DeductPointDetail> deductPointDetails = new ArrayList<>();

	public static DeductPoint create(
			@NonNull final Long userNo,  // 회원번호
			@NonNull final Long deductPoint  // 차감포인트
	) {
		return entityBuilder()
				.userNo(userNo) // 회원번호
				.deductPoint(deductPoint) // 차감포인트
				.deductStatus(DeductPointStatus.DEDUCT) // 포인트 사용 상태
				.build();
	}

	@Override public Long getId() {
		return deductPointNo;
	}
}
