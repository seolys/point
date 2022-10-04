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
import seolnavy.point.util.TestTransactionUtils;

@Transactional
@SpringBootTest
@DisplayName("[Facade] 포인트 적립 테스트")
class EarnPointCommandFacadeTest {

	@Autowired private PointCommandFacade pointCommandFacade;

	@Autowired private EarnPointRepository earnPointRepository;
	@Autowired private UserRepository userRepository;
	@Autowired private UserRedisRepository userRedisRepository;
	@Autowired private PointHistoryRepository pointHistoryRepository;

	@Autowired private PointTestSupport pointTestSupport;


	@Test
	@DisplayName("포인트 적립")
	void earnPoint_success() {
		// given
		final var userNo = 1L;
		long currentUserRemainPoint = pointTestSupport.findUserRemainPoint(userNo);

		final var command = RegisterPoint.of(UUID.randomUUID().toString(), userNo, 2000L);

		// when
		final var earnPointInfo = pointCommandFacade.earnPoint(command);
		currentUserRemainPoint += command.getEarnPoint();
		TestTransactionUtils.end();

		// then
		checkEarnPointInfo(command, earnPointInfo);
		checkEarnPointEntity(command, earnPointInfo.getEarnPointNo());
		checkUserRemainPoint(command.getUserNo(), currentUserRemainPoint);
		checkPointHistory(command, earnPointInfo.getEarnPointNo());
	}

	private void checkPointHistory(final RegisterPoint command, final Long earnPointNo) {
		final var pointHistory = pointHistoryRepository.findByEarnPointNo(earnPointNo).get();
		assertThat(pointHistory.getHistoryType()).isEqualTo(PointHistoryType.EARN);
		assertThat(pointHistory.getPoint()).isEqualTo(command.getEarnPoint());
	}

	private void checkUserRemainPoint(final Long userNo, final long currentUserRemainPoint) {
		final var rdbmsRemainPoint = userRepository.findById(userNo).get().getRemainPoint();
		final var redisRemainPoint = userRedisRepository.getUserRemainPoint(userNo).get();
		assertThat(rdbmsRemainPoint).isEqualTo(currentUserRemainPoint);
		assertThat(redisRemainPoint).isEqualTo(currentUserRemainPoint);
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
