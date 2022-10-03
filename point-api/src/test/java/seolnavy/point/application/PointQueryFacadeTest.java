package seolnavy.point.application;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@SpringBootTest
@DisplayName("포인트 조회 Facade 테스트")
class PointQueryFacadeTest {

	@Autowired private PointQueryFacade pointQueryFacade;

	@Test
	void getUserRemainPoint() {
		final var userRemainPoint = pointQueryFacade.getUserRemainPoint(1L);
		assertThat(userRemainPoint).isNotNull();
	}

	@Test
	void getUserPointHistory() {
		final var userPointHistory = pointQueryFacade.getUserPointHistory(1L, Pageable.ofSize(10));
		assertThat(userPointHistory).isNotNull();
	}

}