package seolnavy.point.application;

import static org.assertj.core.api.Assertions.*;

import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import seolnavy.point.domain.deduct.DeductPointCommand.CancelDeductPoint;
import seolnavy.point.domain.deduct.DeductPointStatus;
import seolnavy.point.domain.history.PointHistoryType;
import seolnavy.point.infra.deduct.DeductPointRepository;
import seolnavy.point.infra.history.PointHistoryRepository;
import seolnavy.point.util.TestTransactionUtils;

@Transactional
@SpringBootTest
@DisplayName("포인트 차감 취소 Facade 테스트")
class CancelDeductPointCommandFacadeTest {

	@Autowired private PointCommandFacade pointCommandFacade;

	@Autowired private DeductPointRepository deductPointRepository;
	@Autowired private PointHistoryRepository pointHistoryRepository;

	@Autowired private PointTestSupport pointTestSupport;

	@Test
	@DisplayName("포인트 차감 취소")
	void cancelDeductPoint() {
		// given
		final var userNo = 1L;
		final var deductPoint = 500L;
		// 현재 포인트 조회
		final var currentRemainPoint = new AtomicLong(pointTestSupport.findUserRemainPoint(userNo));
		// 취소할 포인트 사용 생성.
		final Long deductPointNo = earnAndDeductPoint(userNo, deductPoint, currentRemainPoint);

		// when
		final var command = CancelDeductPoint.of(userNo, deductPointNo);
		pointCommandFacade.cancelDeductPoint(command);
		currentRemainPoint.addAndGet(deductPoint);
		TestTransactionUtils.end();

		// then
		checkCancelDeductPointInfo(deductPointNo);
		checkUserRemainPoint(userNo, currentRemainPoint.get());
		checkPointHistory(deductPointNo);
	}

	private Long earnAndDeductPoint(final long userNo, final long deductPoint, final AtomicLong currentRemainPoint) {
		// 포인트 증가
		final long earnPoint = 1000L;
		pointTestSupport.earnPoint(userNo, earnPoint);
		currentRemainPoint.addAndGet(earnPoint);
		pointTestSupport.earnPoint(userNo, earnPoint);
		currentRemainPoint.addAndGet(earnPoint);
		TestTransactionUtils.endAndStart();

		// 포인트 사용
		final var deductPointNo = pointTestSupport.deductPoint(userNo, deductPoint).getDeductPointNo();
		currentRemainPoint.addAndGet(-deductPoint);
		TestTransactionUtils.endAndStart();

		final var userRemainPoint = pointTestSupport.findUserRemainPoint(userNo);
		assertThat(userRemainPoint).isEqualTo(currentRemainPoint.get());
		return deductPointNo;
	}

	private void checkPointHistory(final Long deductPointNo) {
		final var pointHistory = pointHistoryRepository.findByDeductPointNo(deductPointNo).get();
		assertThat(pointHistory.getHistoryType()).isEqualTo(PointHistoryType.DEDUCT_CANCEL);
	}

	private void checkUserRemainPoint(final long userNo, final long expectedRemainPoint) {
		final var userRemainPoint = pointTestSupport.findUserRemainPoint(userNo);
		assertThat(userRemainPoint).isEqualTo(expectedRemainPoint);
	}

	private void checkCancelDeductPointInfo(final Long deductPointNo) {
		final var findDeductPointEntity = deductPointRepository.findById(deductPointNo).get();
		assertThat(findDeductPointEntity.getDeductStatus()).isEqualTo(DeductPointStatus.DEDUCT_CANCEL);
	}

}