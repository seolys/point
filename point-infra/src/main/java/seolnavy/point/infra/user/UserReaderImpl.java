package seolnavy.point.infra.user;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import seolnavy.point.domain.user.User;
import seolnavy.point.domain.user.UserReader;

@Component
@RequiredArgsConstructor
public class UserReaderImpl implements UserReader {

	private final UserRepository userRepository;
	private final UserRedisRepository redisRepository;

	@Override public Optional<User> findUserById(final Long userNo) {
		return userRepository.findById(userNo);
	}

	@Override public Long getRemainPoint(final Long userNo) {
		final var userRemainPoint = redisRepository.getUserRemainPoint(userNo);
		if (userRemainPoint.isPresent()) {
			return userRemainPoint.get();
		}
		final var remainPoint = userRepository.findById(userNo)
				.map(User::getRemainPoint)
				.orElse(0L);
		redisRepository.setUserPoint(userNo, remainPoint);
		return remainPoint;
	}

}
