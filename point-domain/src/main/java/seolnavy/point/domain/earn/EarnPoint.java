package seolnavy.point.domain.earn;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import seolnavy.point.domain.BaseEntity;

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

	@Column(name = "EXPIRATION_DATE", nullable = false)
	private LocalDateTime expirationDate; // 만료일자

	@Version
	@Column(name = "VERSION", nullable = false)
	private Long version; // 버전

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
				.expirationDate(LocalDateTime.now().plusYears(1)) // 만료일자
				.build();
	}

	@Override public Long getId() {
		return earnPointNo;
	}
}
