package seolnavy.point.domain.history;

import static org.assertj.core.api.Assertions.*;

import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class PointHistoryTest {

	static Stream<PointHistoryType> search_invalidParameter_fail_arguments() {
		return Stream.of(PointHistoryType.EARN, PointHistoryType.DEDUCT, PointHistoryType.EXPIRE);
	}

	@MethodSource("search_invalidParameter_fail_arguments")
	@ParameterizedTest
	void create(final PointHistoryType historyType) {
		// given
		final var userNo = 1L;
		final var earnPointNo = 2L;
		final var deductPointNo = 3L;
		final var point = 1000L;

		// when
		final var pointHistory = PointHistory.create(
				userNo,
				earnPointNo,
				deductPointNo,
				historyType,
				point
		);

		// then
		assertThat(pointHistory.getUserNo()).isEqualTo(userNo);
		assertThat(pointHistory.getEarnPointNo()).isEqualTo(earnPointNo);
		assertThat(pointHistory.getDeductPointNo()).isEqualTo(deductPointNo);
		assertThat(pointHistory.getHistoryType()).isEqualTo(historyType);
		assertThat(pointHistory.getPoint()).isEqualTo(point);
	}

	@Test
	void deductCancel_success() {
		// given
		final var pointHistory = PointHistory.entityBuilder().historyType(PointHistoryType.DEDUCT).build();

		// when
		pointHistory.deductCancel();

		// then
	}

	@Test
	void deductCancel_fail() {
	}
}