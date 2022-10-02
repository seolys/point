package seolnavy.point.infra.earn;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import seolnavy.point.domain.earn.EarnPointReader;

@Component
@RequiredArgsConstructor
public class EarnPointReaderImpl implements EarnPointReader {

	private final EarnPointRepository earnPointRepository;

	@Override public boolean existsByEarnUuid(final String earnUuid) {
		return earnPointRepository.existsByEarnUuid(earnUuid);
	}
}
