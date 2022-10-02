package seolnavy.point.infra.earn;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import seolnavy.point.domain.earn.EarnPoint;
import seolnavy.point.domain.earn.EarnPointStore;

@Component
@RequiredArgsConstructor
public class EarnPointStoreImpl implements EarnPointStore {

	private final EarnPointRepository earnPointRepository;

	@Override public EarnPoint save(final EarnPoint earnPoint) {
		return earnPointRepository.save(earnPoint);
	}

}
