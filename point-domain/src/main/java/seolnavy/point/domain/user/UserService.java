package seolnavy.point.domain.user;

public interface UserService {

	/**
	 * 잔액 조회
	 */
	Long getRemainPoint(Long userNo);

	/**
	 * 회원 포인트 저장
	 */
	void saveUserPoint(UserCommand.SaveUserPoint savePoint);

}
