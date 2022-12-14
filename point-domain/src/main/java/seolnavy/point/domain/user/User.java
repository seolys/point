package seolnavy.point.domain.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.Comment;
import seolnavy.point.domain.BaseEntity;
import seolnavy.point.domain.user.exception.RemainPointsIsMinusException;

@Getter
@Entity
@Table(name = "USER_POINT")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(builderMethodName = "entityBuilder", toBuilder = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class User extends BaseEntity<Long> {

	@Id
	@EqualsAndHashCode.Include
	@Comment("회원번호")
	@Column(name = "USER_NO", nullable = false)
	private Long userNo;

	@Comment("잔여포인트")
	@Column(name = "REMAIN_POINT", nullable = false)
	private Long remainPoint;

	@Version
	@Comment("버전")
	@Column(name = "VERSION", nullable = false)
	private Long version;

	public void increasePoint(final Long point) {
		this.remainPoint += point;
	}

	public void decreasePoint(final Long point) {
		this.remainPoint -= point;
		if (this.remainPoint < 0) {
			throw new RemainPointsIsMinusException(userNo);
		}
	}

	public static User create(
			@NonNull final Long userNo,  // 회원번호
			@NonNull final Long remainPoint  // 잔여포인트
	) {
		return entityBuilder()
				.userNo(userNo) // 회원번호
				.remainPoint(remainPoint) // 잔여포인트
				.build();
	}

	@Override public Long getId() {
		return userNo;
	}
}
