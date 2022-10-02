package seolnavy.point.infra.deduct;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import seolnavy.point.domain.deduct.DeductPoint;

public interface DeductPointRepository extends JpaRepository<DeductPoint, Long> {

	boolean existsByDeductUuid(String deductUuid);

	Optional<DeductPoint> findByUserNoAndDeductPointNo(Long userNo, Long deductPointNo);
	
}
