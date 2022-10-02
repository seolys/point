package seolnavy.point.domain.earn;

import java.util.List;

public interface EarnPointReader {

	boolean existsByEarnUuid(String earnUuid);

	List<EarnPoint> findUserActivePoints(Long userNo);

}
