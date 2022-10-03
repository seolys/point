package seolnavy.point.domain.earn.exception;

public class PointsAlreadyEarnedException extends RuntimeException {

	public PointsAlreadyEarnedException(final String earnUuid) {
		this(earnUuid, null);
	}

	public PointsAlreadyEarnedException(final String earnUuid, final Throwable cause) {
		super("이미 등록된 포인트입니다. earnUuid=" + earnUuid, cause);
	}

}
