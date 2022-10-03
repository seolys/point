package seolnavy.point.domain.earn;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("적립 포인트")
class EarnPointTest {

	@Test
	@DisplayName("포인트 차감 - 잔여포인트가 차감할 포인트보다 많음.")
	void deductPoint_remainingPointsMoreThanDeductPoints_success() {
		final var earnPoint = EarnPoint.entityBuilder().remainPoint(10_000L).build();

		assertThat(earnPoint.deductPoint(3_000L))
				.as("잔여 포인트가 10,000원일 때, 3,000원을 차감 시도하면 차감금액은 3,000원 이다.")
				.isEqualTo(3_000L);
	}

	@Test
	@DisplayName("포인트 차감 - 잔여포인트가 차감할 포인트보다 적음.")
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