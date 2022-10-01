package seolnavy.point.domain.earn;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seolnavy.point.domain.earn.EarnPointCommand.RegisterPoint;
import seolnavy.point.domain.earn.EarnPointInfo.Main;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EarnPointServiceImpl implements EarnPointService {

	private final EarnPointStore earnPointStore;

	@Override
	@Transactional
	public Main registerEarnPoint(final RegisterPoint request) {
		final var saveEarnPoint = earnPointStore.save(request.toEntity());
		return EarnPointInfo.Main.of(saveEarnPoint);
	}

}
