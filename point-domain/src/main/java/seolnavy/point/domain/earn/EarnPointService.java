package seolnavy.point.domain.earn;

import static seolnavy.point.domain.earn.EarnPointCommand.DeductPoint;

import java.util.List;
import seolnavy.point.domain.earn.EarnPointCommand.RegisterPoint;
import seolnavy.point.domain.earn.EarnPointCommand.RestorePoint;
import seolnavy.point.domain.earn.EarnPointInfo.DeductPointResult;
import seolnavy.point.domain.earn.EarnPointInfo.Main;
import seolnavy.point.domain.earn.EarnPointInfo.RestorePointsResult;

public interface EarnPointService {

	Main registerEarnPoint(RegisterPoint command);

	List<DeductPointResult> deductPoints(DeductPoint command);

	RestorePointsResult restorePoints(RestorePoint command);

}
