package seolnavy.point.infra.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import seolnavy.point.domain.user.User;
import seolnavy.point.domain.user.UserStore;

@Component
@RequiredArgsConstructor
public class UserStoreImpl implements UserStore {

	private final UserRepository userRepository;
	private final UserRedisRepository userRedisRepository;

	@Override public User save(final User user) {
		return userRepository.save(user);
	}

	@Override public void setUserPointToCache(final Long userNo, final Long remainPoint) {
		userRedisRepository.saveRemainPoint(userNo, remainPoint);
	}

}
