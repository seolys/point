package seolnavy.point;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import seolnavy.point.application.PointCommandFacade;
import seolnavy.point.application.PointQueryFacade;
import seolnavy.point.domain.deduct.DeductPointCommand.CancelDeductPoint;
import seolnavy.point.domain.deduct.DeductPointCommand.DeductPointRequest;
import seolnavy.point.domain.earn.EarnPointCommand.RegisterPoint;

@SpringBootTest
public class IntegrationTest {

	@Autowired private PointCommandFacade pointCommandFacade;
	@Autowired private PointQueryFacade pointQueryFacade;

	private final Long userNo = 1L;

	@Test
	public void integration() {
		Long expectedRemainPoint = 0L;

		/* ============ <1. 적립> ============ */
		// 1,000원씩 20번 포인트 적립
		final List<Long> earnPointNos = new ArrayList<>();
		for (int i = 0; i < 20; i++) {
			final var earnUuid = UUID.randomUUID().toString();
			final var earnPoint = 1_000L;
			final var command = RegisterPoint.of(earnUuid, userNo, earnPoint);
			final var result = pointCommandFacade.earnPoint(command);
			earnPointNos.add(result.getEarnPointNo());
			expectedRemainPoint += earnPoint;
		}
		var userRemainPoint = pointQueryFacade.getUserRemainPoint(userNo);
		assertThat(userRemainPoint).isEqualTo(expectedRemainPoint);
		assertThat(userRemainPoint)
				.as("1,000 * 20 = 20,000")
				.isEqualTo(20_000L);

		// 적립 후 내역 조회
		final var useearnPointHistory = pointQueryFacade.getUserPointHistory(userNo, Pageable.ofSize(50));
		assertThat(useearnPointHistory.size()).isEqualTo(20);

		/* ============ <2. 사용> ============ */
		// 포인트 19,000원 사용
		final var deductPointRequest = DeductPointRequest.of(generateUUID(), userNo, 19_000L);
		final var deductPointInfo = pointCommandFacade.deductPoint(deductPointRequest);
		expectedRemainPoint -= deductPointRequest.getDeductPoint();

		userRemainPoint = pointQueryFacade.getUserRemainPoint(userNo);
		assertThat(userRemainPoint).isEqualTo(expectedRemainPoint);
		assertThat(userRemainPoint)
				.as("20,000 - 19,000 = 1,000")
				.isEqualTo(1_000L);

		// 사용 후 내역 조회
		final var deductPointHistory = pointQueryFacade.getUserPointHistory(userNo, Pageable.ofSize(50));
		assertThat(deductPointHistory.size()).isEqualTo(21);

		/* ============ <3. 사용취소> ============ */
		// 포인트 19,000원 사용취소
		final var cancelDeductPointRequest = CancelDeductPoint.of(userNo, deductPointInfo.getDeductPointNo());
		pointCommandFacade.cancelDeductPoint(cancelDeductPointRequest);
		expectedRemainPoint += deductPointRequest.getDeductPoint();

		userRemainPoint = pointQueryFacade.getUserRemainPoint(userNo);
		assertThat(userRemainPoint).isEqualTo(expectedRemainPoint);
		assertThat(userRemainPoint)
				.as("20,000 - 19,000 + 19,000 = 20,000")
				.isEqualTo(20_000L);

		// 취소 후 내역 조회
		final var cancelPointHistory = pointQueryFacade.getUserPointHistory(userNo, Pageable.ofSize(50));
		assertThat(cancelPointHistory.size()).isEqualTo(20);

		// 취소 후 내역 조회
		final var pagingPointHistory = pointQueryFacade.getUserPointHistory(userNo, Pageable.ofSize(10));
		assertThat(pagingPointHistory.size()).isEqualTo(10);
		assertThat(pagingPointHistory.get(0).getPointHistoryNo()).isEqualTo(20L);
	}

	private String generateUUID() {
		return UUID.randomUUID().toString();
	}

}
