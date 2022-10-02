package seolnavy.point.infra.earn;

import org.springframework.data.jpa.repository.JpaRepository;
import seolnavy.point.domain.earn.EarnPoint;

public interface EarnPointRepository extends JpaRepository<EarnPoint, Long> {

	boolean existsByEarnUuid(String earnUuid);

}
