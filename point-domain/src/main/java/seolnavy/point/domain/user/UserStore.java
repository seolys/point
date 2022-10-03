package seolnavy.point.domain.user;

public interface UserStore {

	User save(User user);

	void setUserPointToCache(Long userNo, Long remainPoint);

}
