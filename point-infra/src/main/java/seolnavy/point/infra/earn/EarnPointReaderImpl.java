package seolnavy.point.infra.earn;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import seolnavy.point.domain.UseYn;
import seolnavy.point.domain.earn.EarnPoint;
import seolnavy.point.domain.earn.EarnPointReader;

@Component
@RequiredArgsConstructor
public class EarnPointReaderImpl implements EarnPointReader {

	private final EarnPointRepository earnPointRepository;

	@Override public boolean existsByEarnUuid(final String earnUuid) {
		return earnPointRepository.existsByEarnUuid(earnUuid);
	}

	@Override public List<EarnPoint> findUserActivePoints(final Long userNo) {
		final var notExpiration = UseYn.N;
		final var remainPoint = 0L;
		return earnPointRepository.findActivePointsByUserNoAndExpirationYnAndRemainPointGreaterThanOrderByEarnPointNoAsc(
				userNo, notExpiration, remainPoint);
	}

	@Override public List<EarnPoint> findAllByIds(final List<Long> earnPointNos) {
		return earnPointRepository.findByEarnPointNoIn(earnPointNos);
	}

}
