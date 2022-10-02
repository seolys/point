package seolnavy.point.domain.deduct;

import seolnavy.point.domain.deduct.DeductPointCommand.RegisterDeductPoint;
import seolnavy.point.domain.deduct.DeductPointInfo.Main;

public interface DeductPointService {

	Main registerDeductPoint(RegisterDeductPoint registerDeductPoint);

	void cancelDeductPoint(DeductPointCommand.CancelDeductPoint cancelDeductPoint);

	boolean existsByDeductUuid(String deductUuid);
}
