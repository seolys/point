package seolnavy.point.domain.deduct;

public interface DeductPointStore {

	DeductPoint save(DeductPoint deductPoint);
	
	DeductPointDetail save(DeductPointDetail deductPointDetail);

}
