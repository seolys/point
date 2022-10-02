package seolnavy.point.infra.deduct;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import seolnavy.point.domain.deduct.DeductPointReader;

@Component
@RequiredArgsConstructor
public class DeductPointReaderImpl implements DeductPointReader {

	private final DeductPointRepository deductPointRepository;

	@Override public boolean existsByDeductUuid(final String deductUuid) {
		return deductPointRepository.existsByDeductUuid(deductUuid);
	}
}
