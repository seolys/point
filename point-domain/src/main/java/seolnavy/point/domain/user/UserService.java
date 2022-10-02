package seolnavy.point.domain.user;

import seolnavy.point.domain.user.UserCommand.UpdatePoint;
import seolnavy.point.domain.user.UserInfo.Main;

public interface UserService {

	/**
	 * 회원별 포인트 조회
	 * @param userNo
	 * @return
	 */
	Long getRemainPoint(Long userNo);

	/**
	 * 회원 포인트 증가
	 */
	Main increaseUserPoint(UpdatePoint command);

	/**
	 * 회원 포인트 감소
	 */
	Main decreaseUserPoint(UpdatePoint command);
}
