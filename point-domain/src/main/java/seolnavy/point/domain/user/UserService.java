package seolnavy.point.domain.user;

public interface UserService {

	/**
	 * 회원별 포인트 조회
	 * @param userNo
	 * @return
	 */
	Long getRemainPoint(Long userNo);

	/**
	 * 회원 포인트 저장
	 */
	void saveUserPoint(UserCommand.SaveUserPoint savePoint);

}
