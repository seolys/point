package seolnavy.point.infra.deduct;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import seolnavy.point.domain.deduct.DeductPoint;
import seolnavy.point.domain.deduct.DeductPointReader;

@Component
@RequiredArgsConstructor
public class DeductPointReaderImpl implements DeductPointReader {

	private final DeductPointRepository deductPointRepository;

	@Override public boolean existsByDeductUuid(final String deductUuid) {
		return deductPointRepository.existsByDeductUuid(deductUuid);
	}

	@Override public Optional<DeductPoint> findById(final Long userNo, final Long deductPointNo) {
		return deductPointRepository.findByUserNoAndDeductPointNo(userNo, deductPointNo);
	}
}
