package seolnavy.point.domain.earn;

import seolnavy.point.domain.earn.EarnPointCommand.RegisterPoint;
import seolnavy.point.domain.earn.EarnPointInfo.Main;

public interface EarnPointService {

	Main registerEarnPoint(RegisterPoint request);

}
