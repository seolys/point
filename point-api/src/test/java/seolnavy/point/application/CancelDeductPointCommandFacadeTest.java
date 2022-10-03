package seolnavy.point.application;

import static org.assertj.core.api.Assertions.*;

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
import seolnavy.point.infra.user.UserRedisRepository;
import seolnavy.point.infra.user.UserRepository;

@Transactional
@SpringBootTest
@DisplayName("포인트 차감 취소 Facade 테스트")
class CancelDeductPointCommandFacadeTest {

	@Autowired private PointCommandFacade pointCommandFacade;

	@Autowired private DeductPointRepository deductPointRepository;
	@Autowired private UserRepository userRepository;
	@Autowired private UserRedisRepository userRedisRepository;
	@Autowired private PointHistoryRepository pointHistoryRepository;

	@Autowired private PointCommandTestSupport pointCommandTestSupport;

	@Test
	@DisplayName("포인트 차감 취소")
	void cancelDeductPoint() {
		// given
		final var userNo = 1L;
		final var earnPoint = 1000L;
		final var deductPoint = 500L;
		pointCommandTestSupport.earnPoint(userNo, earnPoint);
		pointCommandTestSupport.earnPoint(userNo, earnPoint);
		final var deductPointNo = pointCommandTestSupport.deductPoint(userNo, deductPoint).getDeductPointNo();

		final var user = userRepository.findById(userNo).get();
		assertThat(user.getRemainPoint()).isEqualTo(1500L);

		// when
		final var command = CancelDeductPoint.of(userNo, deductPointNo);
		pointCommandFacade.cancelDeductPoint(command);

		// then
		checkCancelDeductPointInfo(deductPointNo);
		checkUserRemainPoint(userNo, 2000L);
		checkPointHistory(deductPointNo);
	}

	private void checkPointHistory(final Long deductPointNo) {
		final var pointHistory = pointHistoryRepository.findByDeductPointNo(deductPointNo).get();
		assertThat(pointHistory.getHistoryType()).isEqualTo(PointHistoryType.DEDUCT_CANCEL);
	}

	private void checkUserRemainPoint(final long userNo, final long expectedRemainPoint) {
		final var userRemainPoint = userRedisRepository.getUserRemainPoint(userNo).get();
		assertThat(userRemainPoint).isEqualTo(expectedRemainPoint);
	}

	private void checkCancelDeductPointInfo(final Long deductPointNo) {
		final var findDeductPointEntity = deductPointRepository.findById(deductPointNo).get();
		assertThat(findDeductPointEntity.getDeductStatus()).isEqualTo(DeductPointStatus.DEDUCT_CANCEL);
	}

}