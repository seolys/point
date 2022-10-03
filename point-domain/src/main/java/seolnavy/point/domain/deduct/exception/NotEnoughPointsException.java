package seolnavy.point.domain.deduct.exception;

public class NotEnoughPointsException extends RuntimeException {

	public NotEnoughPointsException(final Long userNo) {
		super(String.format("가용 포인트가 없습니다. userNo=%s, remainPoint=0", userNo));
	}

	public NotEnoughPointsException(final Long userNo, final Long remainPoint, final Long deductPoint) {
		super(String.format("가용 포인트가 부족합니다. userNo=%s, remainPoint=%s, deductPoint=%s", userNo, remainPoint, deductPoint));
	}
}