package seolnavy.point.domain.user;

public interface UserStore {

	User save(User user);

	void increaseUserPoint(Long userNo, Long remainPoint);

}
