package seolnavy.point;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import seolnavy.point.application.PointCommandFacade;
import seolnavy.point.application.PointTestSupport;
import seolnavy.point.domain.deduct.DeductPointCommand.CancelDeductPoint;
import seolnavy.point.domain.deduct.DeductPointCommand.DeductPointRequest;
import seolnavy.point.domain.earn.EarnPointCommand.RegisterPoint;
import seolnavy.point.util.TestTransactionUtils;

@Transactional
@SpringBootTest
@DisplayName("[Integration] 포인트 테스트")
public class IntegrationTest {

	@Autowired private PointCommandFacade pointCommandFacade;
	@Autowired private PointTestSupport pointTestSupport;

	private final Long userNo = 1L;

	@Test
	@DisplayName("포인트 적립, 사용, 취소")
	public void integration() {
		// 현재 포인트 조회
		Long expectedRemainPoint = pointTestSupport.findUserRemainPoint(userNo);

		/* ============ <1. 적립> ============ */
		// 1,000원씩 20번 포인트 적립
		final List<Long> earnPointNos = new ArrayList<>();
		for (int i = 0; i < 20; i++) {
			final var earnUuid = UUID.randomUUID().toString();
			final var earnPoint = 1_000L;
			final var command = RegisterPoint.of(earnUuid, userNo, earnPoint);
			final var result = pointCommandFacade.earnPoint(command);
			TestTransactionUtils.endAndStart();

			earnPointNos.add(result.getEarnPointNo());
			expectedRemainPoint += earnPoint;
		}

		var userRemainPoint = pointTestSupport.findUserRemainPoint(userNo);
		assertThat(userRemainPoint).isEqualTo(expectedRemainPoint);

		/* ============ <2. 사용> ============ */
		// 포인트 19,000원 사용
		final var deductPointRequest = DeductPointRequest.of(generateUUID(), userNo, 19_000L);
		final var deductPointInfo = pointCommandFacade.deductPoint(deductPointRequest);
		expectedRemainPoint -= deductPointRequest.getDeductPoint();
		TestTransactionUtils.endAndStart();

		userRemainPoint = pointTestSupport.findUserRemainPoint(userNo);
		assertThat(userRemainPoint).isEqualTo(expectedRemainPoint);

		/* ============ <3. 사용취소> ============ */
		// 포인트 19,000원 사용취소
		final var cancelDeductPointRequest = CancelDeductPoint.of(userNo, deductPointInfo.getDeductPointNo());
		pointCommandFacade.cancelDeductPoint(cancelDeductPointRequest);
		expectedRemainPoint += deductPointRequest.getDeductPoint();
		TestTransactionUtils.end();

		userRemainPoint = pointTestSupport.findUserRemainPoint(userNo);
		assertThat(userRemainPoint).isEqualTo(expectedRemainPoint);
	}

	private String generateUUID() {
		return UUID.randomUUID().toString();
	}

}
