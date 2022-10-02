package seolnavy.point.domain.earn;

import static seolnavy.point.domain.earn.EarnPointCommand.DeductPoint;

import java.util.List;
import seolnavy.point.domain.earn.EarnPointCommand.RegisterPoint;
import seolnavy.point.domain.earn.EarnPointCommand.RestorePoint;
import seolnavy.point.domain.earn.EarnPointInfo.DeductPointResult;
import seolnavy.point.domain.earn.EarnPointInfo.Main;

public interface EarnPointService {

	Main registerEarnPoint(RegisterPoint command);

	List<DeductPointResult> deductPoints(DeductPoint command);

	void restorePoints(RestorePoint command);

}
