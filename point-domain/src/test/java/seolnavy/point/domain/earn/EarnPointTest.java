package seolnavy.point.domain.earn;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import seolnavy.point.domain.UseYn;

@DisplayName("[Domain] 포인트 적립")
class EarnPointTest {

	@Test
	@DisplayName("적립 포인트 생성")
	void create_success() {
		final var earnUuid = UUID.randomUUID().toString();
		final var userNo = 1L;
		final var point = 1000L;

		final var earnPoint = EarnPoint.create(earnUuid, userNo, point);

		assertThat(earnPoint.getEarnUuid()).isEqualTo(earnUuid);
		assertThat(earnPoint.getUserNo()).isEqualTo(userNo);
		assertThat(earnPoint.getEarnPoint()).isEqualTo(point);
		assertThat(earnPoint.getRemainPoint()).isEqualTo(point);
		assertThat(earnPoint.getExpirationYn()).isEqualTo(UseYn.N);
		assertThat(earnPoint.getExpirationDate()).isEqualTo(LocalDate.now().plusYears(1));
	}

	@Test
	@DisplayName("포인트 차감 - 만료된 포인트에 차감요청")
	void deductPoint_expiration_fail() {
		final var earnPoint = EarnPoint.entityBuilder()
				.remainPoint(10_000L)
				.expirationYn(UseYn.Y)
				.build();

		assertThat(earnPoint.deductPoint(3_000L)).isEqualTo(0L);
	}

	@Test
	@DisplayName("포인트 차감 - 잔여포인트가 차감할 포인트보다 많음")
	void deductPoint_remainingPointsMoreThanDeductPoints_success() {
		final var earnPoint = EarnPoint.entityBuilder().remainPoint(10_000L).build();

		assertThat(earnPoint.deductPoint(3_000L))
				.as("잔여 포인트가 10,000원일 때, 3,000원을 차감 시도하면 차감금액은 3,000원 이다.")
				.isEqualTo(3_000L);
	}

	@Test
	@DisplayName("포인트 차감 - 잔여포인트가 차감할 포인트보다 적음")
	void deductPoint_remainingPointsLessThanDeductPoints_success() {
		final var earnPoint = EarnPoint.entityBuilder().remainPoint(3_000L).build();

		assertThat(earnPoint.deductPoint(10_000L))
				.as("잔여 포인트가 3,000원일 때, 10,000원을 차감 시도하면 차감금액은 3,000원 이다.")
				.isEqualTo(3000L);
	}

	@Test
	@DisplayName("포인트 차감 - 잔여포인트가 차감할 포인트와 같음")
	void deductPoint_remainingPointsEqualsDeductPoints_success() {
		final var earnPoint = EarnPoint.entityBuilder().remainPoint(3_000L).build();

		assertThat(earnPoint.deductPoint(3_000L))
				.as("잔여 포인트가 3,000원일 때, 3,000원을 차감 시도하면 차감금액은 3,000원 이다.")
				.isEqualTo(3000L);
	}

	@Test
	@DisplayName("포인트 차감 - 차감할 포인트가 없음")
	void deductPoint_remainingPointsIsZero_success() {
		final var earnPoint = EarnPoint.entityBuilder().remainPoint(0L).build();

		assertThat(earnPoint.deductPoint(3_000L))
				.as("잔여 포인트가 없으면 차감할 수 없다.")
				.isEqualTo(0L);
	}

}