package seolnavy.point.domain.earn;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seolnavy.point.domain.earn.EarnPointCommand.RegisterPoint;
import seolnavy.point.domain.earn.EarnPointInfo.Main;
import seolnavy.point.domain.earn.exception.PointsAlreadyEarnedException;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EarnPointServiceImpl implements EarnPointService {

	private final EarnPointReader earnPointReader;
	private final EarnPointStore earnPointStore;

	@Override
	@Transactional
	public Main registerEarnPoint(final RegisterPoint request) {
		final var exists = earnPointReader.existsByEarnUuid(request.getEarnUuid());
		if (exists) {
			throw new PointsAlreadyEarnedException();
		}

		try {
			final var saveEarnPoint = earnPointStore.save(request.toEntity());
			return EarnPointInfo.Main.of(saveEarnPoint);
		} catch (final ConstraintViolationException e) {
			throw new PointsAlreadyEarnedException(e);
		}
	}

}
