package seolnavy.point.domain.user.exception;

public class RemainPointsIsMinusException extends RuntimeException {

	public RemainPointsIsMinusException(final Long userNo) {
		super(String.format("포인트가 마이너스일 수 없습니다. userNo=%s", userNo));
	}

}