package seolnavy.point.domain.deduct;

import java.util.Optional;

public interface DeductPointReader {

	boolean existsByDeductUuid(String deductUuid);

	Optional<DeductPoint> findById(Long userNo, Long deductPointNo);
}
