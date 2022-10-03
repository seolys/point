package seolnavy.point.domain.deduct.exception;

public class PointsAlreadyDeductedException extends RuntimeException {

	public PointsAlreadyDeductedException(final String deductUuid) {
		super("이미 등록된 차감입니다. deductUuid=" + deductUuid);
	}
}
