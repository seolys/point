package seolnavy.point.domain.deduct.exception;

public class DeductPointNotFoundException extends RuntimeException {

	public DeductPointNotFoundException() {
		this(null);
	}

	public DeductPointNotFoundException(final Throwable cause) {
		super(cause);
	}
}