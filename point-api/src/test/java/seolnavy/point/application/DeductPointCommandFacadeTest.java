package seolnavy.point.application;

import static org.assertj.core.api.Assertions.*;

import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import seolnavy.point.domain.deduct.DeductPointCommand.DeductPointRequest;
import seolnavy.point.domain.deduct.exception.NotEnoughPointsException;
import seolnavy.point.domain.history.PointHistoryType;
import seolnavy.point.infra.deduct.DeductPointRepository;
import seolnavy.point.infra.history.PointHistoryRepository;
import seolnavy.point.infra.user.UserRedisRepository;
import seolnavy.point.infra.user.UserRepository;
import seolnavy.point.util.UnitTestUtils;

@Transactional
@SpringBootTest
@DisplayName("포인트 차감 Facade 테스트")
class DeductPointCommandFacadeTest {

	@Autowired private PointCommandFacade pointCommandFacade;

	@Autowired private DeductPointRepository deductPointRepository;
	@Autowired private UserRepository userRepository;
	@Autowired private UserRedisRepository userRedisRepository;
	@Autowired private PointHistoryRepository pointHistoryRepository;

	@Autowired private PointCommandTestSupport pointCommandTestSupport;

	@Test
	@DisplayName("포인트 차감")
	void deductPoint_success() {
		// given
		final var userNo = 1L;
		final var point = 1000L;
		pointCommandTestSupport.earnPoint(userNo, point);

		final var deductUuid = UUID.randomUUID().toString();
		final var command = DeductPointRequest.of(deductUuid, userNo, point);

		// when
		final var deductPointInfo = pointCommandFacade.deductPoint(command);
		UnitTestUtils.endTransaction(); // @TransactionalEventListener 의 동작까지 수행시킨다.

		// then
		checkDeductPointInfo(command, deductPointInfo.getDeductPointNo());
		checkUserRemainPoint(userNo, point - command.getDeductPoint());
		checkPointHistory(command, deductPointInfo.getDeductPointNo());
	}

	@Test
	@DisplayName("포인트 차감 실패 - 차감할 포인트가 남은 포인트보다 큰 경우")
	void deductPoint_fail() {
		// given
		final var userNo = 2L;
		final var point = 1000L;
		pointCommandTestSupport.earnPoint(userNo, point);

		final var deductUuid = UUID.randomUUID().toString();
		final var command = DeductPointRequest.of(deductUuid, userNo, point + 500);

		// when
		assertThatThrownBy(() -> pointCommandFacade.deductPoint(command))
				.isInstanceOf(NotEnoughPointsException.class);
	}

	private void checkPointHistory(final DeductPointRequest command, final Long deductPointNo) {
		final var pointHistory = pointHistoryRepository.findByDeductPointNo(deductPointNo).get();
		assertThat(pointHistory.getHistoryType()).isEqualTo(PointHistoryType.DEDUCT);
		assertThat(pointHistory.getPoint()).isEqualTo(command.getDeductPoint());
	}

	private void checkUserRemainPoint(final long userNo, final long remainPoint) {
		final var findUser = userRepository.findById(userNo).get();
		final var findUserRemainPoint = userRedisRepository.getUserRemainPoint(userNo).get();
		assertThat(findUser.getRemainPoint()).isEqualTo(remainPoint);
		assertThat(findUserRemainPoint).isEqualTo(remainPoint);
	}

	private void checkDeductPointInfo(final DeductPointRequest command, final Long deductPointNo) {
		final var deductPoint = deductPointRepository.findById(deductPointNo).get();
		assertThat(deductPoint.getDeductUuid()).isEqualTo(command.getDeductUuid());
		assertThat(deductPoint.getUserNo()).isEqualTo(command.getUserNo());
		assertThat(deductPoint.getDeductPoint()).isEqualTo(command.getDeductPoint());
	}

}