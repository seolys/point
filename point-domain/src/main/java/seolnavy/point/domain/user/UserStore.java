package seolnavy.point.domain.user;

public interface UserStore {

	void updateUserPoint(User user);

	User save(User user);
}
