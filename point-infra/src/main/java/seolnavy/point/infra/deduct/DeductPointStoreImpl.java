package seolnavy.point.infra.deduct;

import org.springframework.stereotype.Component;
import seolnavy.point.domain.deduct.DeductPoint;
import seolnavy.point.domain.deduct.DeductPointDetail;
import seolnavy.point.domain.deduct.DeductPointStore;

@Component
public class DeductPointStoreImpl implements DeductPointStore {

	@Override public DeductPoint save(final DeductPoint deductPoint) {
		return null;
	}

	@Override public DeductPointDetail save(final DeductPointDetail deductPointDetail) {
		return null;
	}
}
