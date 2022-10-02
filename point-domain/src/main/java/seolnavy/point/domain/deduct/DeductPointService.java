package seolnavy.point.domain.deduct;

import seolnavy.point.domain.deduct.DeductPointCommand.CancelDeductPoint;
import seolnavy.point.domain.deduct.DeductPointCommand.RegisterDeductPoint;
import seolnavy.point.domain.deduct.DeductPointInfo.CancelDeductPointInfo;
import seolnavy.point.domain.deduct.DeductPointInfo.Main;

public interface DeductPointService {

	Main registerDeductPoint(RegisterDeductPoint registerDeductPoint);

	CancelDeductPointInfo cancelDeductPoint(CancelDeductPoint cancelDeductPoint);

	boolean existsByDeductUuid(String deductUuid);
}
