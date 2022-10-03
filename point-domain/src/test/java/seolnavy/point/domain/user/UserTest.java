package seolnavy.point.domain.user;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserTest {

	@Test
	@DisplayName("회원 생성 및 포인트 부여")
	void create_success() {
		// given
		final var userNo = 1L;
		final var remainPoint = 5000L;

		// when
		final var user = User.create(userNo, remainPoint);

		// then
		assertThat(user.getUserNo()).isEqualTo(userNo);
		assertThat(user.getRemainPoint()).isEqualTo(remainPoint);
	}

	@Test
	@DisplayName("포인트 증가 성공")
	void increasePoint_success() {
		// given
		final var user = User.entityBuilder().remainPoint(1000L).build();

		// when
		user.increasePoint(1000L);

		// then
		assertThat(user.getRemainPoint()).isEqualTo(2000L);
	}

	@Test
	@DisplayName("포인트 감소 성공")
	void decreasePoint_success() {
		// given
		final var user = User.entityBuilder().remainPoint(1000L).build();

		// when
		user.decreasePoint(600L);

		// then
		assertThat(user.getRemainPoint()).isEqualTo(400L);
	}

	@Test
	@DisplayName("포인트 감소 실패")
	void decreasePoint_remainPointIsMinus_fail() {
		// given
		final var user = User.entityBuilder().remainPoint(1000L).build();

		// when
		assertThatThrownBy(() -> user.decreasePoint(2000L));
	}

}