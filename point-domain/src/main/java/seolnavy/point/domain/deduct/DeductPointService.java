package seolnavy.point.domain.deduct;

public interface DeductPointService {

	void registerDeductPoint(DeductPointCommand.RegisterDeductPoint registerDeductPoint);

	void cancelDeductPoint(DeductPointCommand.CancelDeductPoint cancelDeductPoint);

}
