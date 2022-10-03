package seolnavy.point.application;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import seolnavy.point.domain.UseYn;
import seolnavy.point.domain.earn.EarnPointCommand.RegisterPoint;
import seolnavy.point.domain.earn.EarnPointInfo.Main;
import seolnavy.point.domain.history.PointHistoryType;
import seolnavy.point.infra.earn.EarnPointRepository;
import seolnavy.point.infra.history.PointHistoryRepository;
import seolnavy.point.infra.user.UserRedisRepository;
import seolnavy.point.infra.user.UserRepository;
import seolnavy.point.util.UnitTestUtils;

@Transactional
@SpringBootTest
@DisplayName("포인트 적립 Facade 테스트")
class EarnPointCommandFacadeTest {

	@Autowired private PointCommandFacade pointCommandFacade;

	@Autowired private EarnPointRepository earnPointRepository;
	@Autowired private UserRepository userRepository;
	@Autowired private UserRedisRepository userRedisRepository;
	@Autowired private PointHistoryRepository pointHistoryRepository;

	@Test
	@DisplayName("포인트 적립")
	void earnPoint_success() {
		// given
		final var earnUuid = UUID.randomUUID().toString();
		final var userNo = 1L;
		final var earnPoint = 2000L;
		final var command = RegisterPoint.of(earnUuid, userNo, earnPoint);

		// when
		final var earnPointInfo = pointCommandFacade.earnPoint(command);
		UnitTestUtils.endTransaction(); // @TransactionalEventListener 의 동작까지 수행시킨다.

		// then
		checkEarnPointInfo(command, earnPointInfo);
		checkEarnPointEntity(command, earnPointInfo.getEarnPointNo());
		checkUserRemainPoint(userNo, earnPoint);
		checkPointHistory(command, earnPointInfo.getEarnPointNo());
	}

	private void checkPointHistory(final RegisterPoint command, final Long earnPointNo) {
		final var pointHistory = pointHistoryRepository.findByEarnPointNo(earnPointNo).get();
		assertThat(pointHistory.getHistoryType()).isEqualTo(PointHistoryType.EARN);
		assertThat(pointHistory.getPoint()).isEqualTo(command.getEarnPoint());
	}

	private void checkUserRemainPoint(final long userNo, final long remainPoint) {
		final var findUser = userRepository.findById(userNo).get();
		final var findUserRemainPoint = userRedisRepository.getUserRemainPoint(userNo).get();
		assertThat(findUser.getRemainPoint()).isEqualTo(remainPoint);
		assertThat(findUserRemainPoint).isEqualTo(remainPoint);
	}

	private void checkEarnPointEntity(final RegisterPoint command, final Long earnPointNo) {
		final var findEarnPointInfo = earnPointRepository.findById(earnPointNo).get();
		assertThat(findEarnPointInfo.getEarnUuid()).isEqualTo(command.getEarnUuid());
		assertThat(findEarnPointInfo.getUserNo()).isEqualTo(command.getUserNo());
		assertThat(findEarnPointInfo.getEarnPoint()).isEqualTo(command.getEarnPoint());
		assertThat(findEarnPointInfo.getRemainPoint()).isEqualTo(command.getEarnPoint());
		assertThat(findEarnPointInfo.getExpirationYn()).isEqualTo(UseYn.N);
		assertThat(findEarnPointInfo.getExpirationDate()).isEqualTo(LocalDate.now().plusYears(1));
	}

	private void checkEarnPointInfo(final RegisterPoint command, final Main earnPointInfo) {
		assertThat(earnPointInfo.getEarnUuid()).isEqualTo(command.getEarnUuid());
		assertThat(earnPointInfo.getUserNo()).isEqualTo(command.getUserNo());
		assertThat(earnPointInfo.getEarnPoint()).isEqualTo(command.getEarnPoint());
	}

}
