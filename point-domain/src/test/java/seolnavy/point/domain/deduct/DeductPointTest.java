package seolnavy.point.domain.deduct;

import static org.assertj.core.api.Assertions.*;

import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import seolnavy.point.domain.deduct.exception.PointsAlreadyCancelledException;

class DeductPointTest {

	@Test
	@DisplayName("포인트 차감 생성")
	void create_success() {
		// given
		final var deductUuid = UUID.randomUUID().toString();
		final var userNo = 1L;
		final var deductPoint = 1000L;

		// when
		final var deduct = DeductPoint.create(deductUuid, userNo, deductPoint);

		// then
		assertThat(deduct.getDeductUuid()).isEqualTo(deductUuid);
		assertThat(deduct.getUserNo()).isEqualTo(userNo);
		assertThat(deduct.getDeductPoint()).isEqualTo(deductPoint);
		assertThat(deduct.getDeductStatus()).isEqualTo(DeductPointStatus.DEDUCT);
	}

	@Test
	@DisplayName("포인트 차감 취소")
	void cancel_success() {
		// given
		final var deduct = DeductPoint.entityBuilder()
				.deductStatus(DeductPointStatus.DEDUCT)
				.build();

		// when
		deduct.cancel();

		// then
		assertThat(deduct.getDeductStatus()).isEqualTo(DeductPointStatus.DEDUCT_CANCEL);
	}

	@Test
	@DisplayName("포인트 차감 중복 취소시 오류 발생")
	void cancel_duplicate_fail() {
		// given
		final var deduct = DeductPoint.entityBuilder()
				.deductStatus(DeductPointStatus.DEDUCT_CANCEL)
				.build();

		// when
		assertThatThrownBy(() -> deduct.cancel())
				.as("중복 취소 요청 시, 예외 발생")
				.isInstanceOf(PointsAlreadyCancelledException.class);
	}
}