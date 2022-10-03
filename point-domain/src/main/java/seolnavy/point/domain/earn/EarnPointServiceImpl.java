package seolnavy.point.domain.earn;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seolnavy.point.domain.earn.EarnPointCommand.DeductPoint;
import seolnavy.point.domain.earn.EarnPointCommand.RegisterPoint;
import seolnavy.point.domain.earn.EarnPointCommand.RestorePoint;
import seolnavy.point.domain.earn.EarnPointInfo.DeductPointResult;
import seolnavy.point.domain.earn.EarnPointInfo.Main;
import seolnavy.point.domain.earn.EarnPointInfo.RestorePointsResult;
import seolnavy.point.domain.earn.EarnPointInfo.RestoredPointInfo;
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
	public Main registerEarnPoint(final RegisterPoint command) {
		final var exists = earnPointReader.existsByEarnUuid(command.getEarnUuid());
		if (exists) {
			log.warn("이미 등록된 포인트입니다. earnUuid={}", command.getEarnUuid());
			throw new PointsAlreadyEarnedException(command.getEarnUuid());
		}

		try {
			final var saveEarnPoint = earnPointStore.save(command.toEntity());
			return EarnPointInfo.Main.of(saveEarnPoint);
		} catch (final ConstraintViolationException e) {
			log.warn("이미 등록된 포인트입니다. earnUuid={}", command.getEarnUuid());
			throw new PointsAlreadyEarnedException(command.getEarnUuid(), e);
		}
	}

	@Transactional
	@Override public List<DeductPointResult> deductPoints(final DeductPoint command) {
		// 가용 포인트 조회.
		final var userNo = command.getUserNo();
		final List<EarnPoint> earnPointList = earnPointReader.findUserActivePoints(userNo);

		// 포인트 컬렉션 생성
		final var earnPointCollection = new EarnPointCollection(userNo, earnPointList);

		// 포인트 차감 및 결과 리턴
		return earnPointCollection.deductPoints(command.getPoint());
	}

	@Transactional
	@Override public RestorePointsResult restorePoints(final RestorePoint command) {
		// DB에서 복구할 대상 조회
		final var earnPoints = earnPointReader.findAllByIds(command.getEarnPointNos());

		final List<RestoredPointInfo> restorePointList = new ArrayList<>();
		final var deductPointMapByEarnPointNo = command.getDeductPointMapByEarnPointNo();
		for (final EarnPoint earnPoint : earnPoints) {
			final var restorePoint = deductPointMapByEarnPointNo.get(earnPoint.getEarnPointNo()); // 복구할 포인트 조회
			final var restoredPoint = earnPoint.restorePoint(restorePoint); // 복구된 포인트 반환

			// 응답할 결과 담아두기.
			restorePointList.add(RestoredPointInfo.of(earnPoint.getEarnPointNo(), restoredPoint));
		}
		return RestorePointsResult.of(restorePointList);
	}

}
