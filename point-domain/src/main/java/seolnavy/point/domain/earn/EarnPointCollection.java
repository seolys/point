package seolnavy.point.domain.earn;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import seolnavy.point.domain.deduct.exception.NotEnoughPointsException;
import seolnavy.point.domain.earn.EarnPointInfo.DeductPointResult;

@Slf4j
@AllArgsConstructor
public class EarnPointCollection {

	private final Long userNo;
	private final List<EarnPoint> earnPointList;

	public List<DeductPointResult> deductPoints(final Long deductPoint) {
		if (earnPointList.isEmpty()) {
			log.warn("가용 포인트가 없습니다. userNo: {}", userNo);
			throw new NotEnoughPointsException();
		}

		// 순차적으로 차감한다.
		final List<DeductPointResult> deductedPointResults = new ArrayList<>();
		var remainingDeductionPoints = deductPoint; // 잔여 차감포인트

		for (final EarnPoint earnPoint : earnPointList) {

			// 차감한다.
			final var deductedPoint = earnPoint.deductPoint(remainingDeductionPoints);
			remainingDeductionPoints -= deductedPoint;

			// 차감된 포인트정보 저장
			deductedPointResults.add(DeductPointResult.of(earnPoint.getEarnPointNo(), deductedPoint));

			// 잔여 차감포인트가 남아있지않다면 종료.
			if (isZero(remainingDeductionPoints)) {
				break;
			}
		}

		// 전부 차감했는데도 차감할 금액이 남아있다면 오류
		if (remainingDeductionPoints > 0L) {
			log.warn("가용 포인트가 부족합니다. userNo: {}, deductPoint: {}, remainingDeductionPoints: {}", userNo, deductPoint, remainingDeductionPoints);
			throw new NotEnoughPointsException();
		}

		return deductedPointResults;
	}

	private boolean isZero(final Long remainingDeductionPoints) {
		return remainingDeductionPoints == 0L;
	}
}
