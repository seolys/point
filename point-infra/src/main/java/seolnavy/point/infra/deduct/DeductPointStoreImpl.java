package seolnavy.point.infra.deduct;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import seolnavy.point.domain.deduct.DeductPoint;
import seolnavy.point.domain.deduct.DeductPointDetail;
import seolnavy.point.domain.deduct.DeductPointStore;

@Component
@RequiredArgsConstructor
public class DeductPointStoreImpl implements DeductPointStore {

	private final DeductPointRepository deductPointRepository;
	private final DeductPointDetailRepository deductPointDetailRepository;

	@Override public DeductPoint save(final DeductPoint deductPoint) {
		return deductPointRepository.save(deductPoint);
	}

	@Override public void saveAll(final List<DeductPointDetail> deductPointDetails) {
		deductPointDetailRepository.saveAll(deductPointDetails);
	}
}
