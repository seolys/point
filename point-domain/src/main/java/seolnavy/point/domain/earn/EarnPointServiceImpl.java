package seolnavy.point.domain.earn;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seolnavy.point.domain.earn.EarnPointCommand.DeductPoint;
import seolnavy.point.domain.earn.EarnPointCommand.RegisterPoint;
import seolnavy.point.domain.earn.EarnPointInfo.DeductPointResult;
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
			log.warn("이미 등록된 포인트입니다. earnUuid: {}", request.getEarnUuid());
			throw new PointsAlreadyEarnedException();
		}

		try {
			final var saveEarnPoint = earnPointStore.save(request.toEntity());
			return EarnPointInfo.Main.of(saveEarnPoint);
		} catch (final ConstraintViolationException e) {
			log.warn("이미 등록된 포인트입니다. earnUuid: {}", request.getEarnUuid());
			throw new PointsAlreadyEarnedException(e);
		}
	}

	@Override public List<DeductPointResult> deductPoints(final DeductPoint request) {
		// 가용 포인트 조회.
		final var userNo = request.getUserNo();
		final List<EarnPoint> earnPointList = earnPointReader.findUserActivePoints(userNo);

		// 포인트 컬렉션 생성
		final var earnPointCollection = new EarnPointCollection(userNo, earnPointList);

		// 포인트 차감
		final var deductedPointResults = earnPointCollection.deductPoints(request.getDeductPoint());

		// 차감 결과 리턴
		return deductedPointResults;
	}


}
