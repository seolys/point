package seolnavy.point.domain.earn.exception;

public class PointsAlreadyEarnedException extends RuntimeException {

	public PointsAlreadyEarnedException() {
		this(null);
	}

	public PointsAlreadyEarnedException(final Throwable cause) {
		super(cause);
	}

}
