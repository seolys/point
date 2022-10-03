package seolnavy.point.domain.deduct.exception;

public class PointsAlreadyCancelledException extends RuntimeException {

	public PointsAlreadyCancelledException(final Long deductPointNo) {
		super("이미 취소된 상태입니다. deductPointNo=" + deductPointNo);
	}
}
