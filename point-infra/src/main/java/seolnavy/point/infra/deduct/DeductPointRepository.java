package seolnavy.point.infra.deduct;

import org.springframework.data.jpa.repository.JpaRepository;
import seolnavy.point.domain.deduct.DeductPoint;

public interface DeductPointRepository extends JpaRepository<DeductPoint, Long> {

	boolean existsByDeductUuid(String deductUuid);
	
}
