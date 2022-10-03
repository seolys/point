package seolnavy.point.application;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;

@SpringBootTest
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