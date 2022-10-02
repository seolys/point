package seolnavy.point.domain.deduct.exception;

public class NotEnoughPointsException extends RuntimeException {

	public NotEnoughPointsException() {
		this(null);
	}

	public NotEnoughPointsException(final Throwable cause) {
		super(cause);
	}
}