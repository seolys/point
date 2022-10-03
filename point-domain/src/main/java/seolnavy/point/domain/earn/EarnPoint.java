package seolnavy.point.domain.earn;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
import seolnavy.point.domain.UseYn;

/**
 * 포인트 증가
 */
@Getter
@Entity
@Table(
		name = "EARN_POINT",
		uniqueConstraints = @UniqueConstraint(name = "UNI_EARN_POINT_UUID", columnNames = {"EARN_UUID"})
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(builderMethodName = "entityBuilder", toBuilder = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class EarnPoint extends BaseEntity<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@Column(name = "EARN_POINT_NO", nullable = false)
	private Long earnPointNo; // 적립포인트번호

	@Column(name = "EARN_UUID", nullable = false)
	private String earnUuid; // 적립_UUID

	@Column(name = "USER_NO", nullable = false)
	private Long userNo; // 회원번호

	@Column(name = "EARN_POINT", nullable = false)
	private Long earnPoint; // 적립포인트

	@Column(name = "REMAIN_POINT", nullable = false)
	private Long remainPoint; // 잔여포인트

	@Column(name = "EXPIRATION_YN", nullable = false)
	@Enumerated(EnumType.STRING)
	@Default
	private UseYn expirationYn = UseYn.N; // 만료여부

	@Column(name = "EXPIRATION_DATE", nullable = false)
	private LocalDate expirationDate; // 만료일자

	@Version
	@Column(name = "VERSION", nullable = false)
	private Long version; // 버전

	/**
	 * 포인트 차감
	 * @param deductionPoints 차감할 포인트
	 * @return 차감된 포인트
	 */
	public long deductPoint(final Long deductionPoints) {
		// 잔액이 차감금액보다 작으면, 잔액을 0원으로 만들고 기존 잔액을 반환.
		if (this.remainPoint < deductionPoints) {
			final long previousRemainPoint = this.remainPoint; // 이전 잔액 백업
			this.remainPoint = 0L;
			return previousRemainPoint; // 이전 잔액 반환
		}
		// 잔액이 차감금액보다 크면, 잔액에서 차감금액을 차감하고 차감금액을 반환.
		this.remainPoint -= deductionPoints;
		return deductionPoints;
	}

	/**
	 * 포인트 복구
	 * @param restorePoint 복구할 포인트
	 * @return 복구한 포인트
	 */
	public long restorePoint(final Long restorePoint) {
		this.remainPoint += restorePoint;

		// 포인트가 복구되었지만, 유효기간이 만료되었다면 실질적인 포인트 복구 금액은 0원이다.
		if (this.expirationYn.isTrue()) {
			return 0L;
		}
		return restorePoint;
	}

	public static EarnPoint create(
			@NonNull final String earnUuid,  // 적립_UUID
			@NonNull final Long userNo,  // 회원번호
			@NonNull final Long earnPoint  // 적립포인트
	) {
		return entityBuilder()
				.earnUuid(earnUuid) // 적립_UUID
				.userNo(userNo) // 회원번호
				.earnPoint(earnPoint) // 적립포인트
				.remainPoint(earnPoint) // 잔여포인트
				.expirationYn(UseYn.N) // 만료여부
				.expirationDate(LocalDate.now().plusYears(1)) // 만료일자
				.build();
	}

	@Override public Long getId() {
		return earnPointNo;
	}
}
