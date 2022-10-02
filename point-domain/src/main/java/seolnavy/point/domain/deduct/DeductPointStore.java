package seolnavy.point.domain.deduct;

import java.util.List;

public interface DeductPointStore {

	DeductPoint save(DeductPoint deductPoint);

	void saveAll(List<DeductPointDetail> toEntityDetails);

}
