package seolnavy.point.infra.deduct;

import org.springframework.data.jpa.repository.JpaRepository;
import seolnavy.point.domain.deduct.DeductPointDetail;

public interface DeductPointDetailRepository extends JpaRepository<DeductPointDetail, Long> {

}
