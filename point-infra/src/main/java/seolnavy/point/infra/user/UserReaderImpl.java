package seolnavy.point.infra.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import seolnavy.point.domain.user.User;
import seolnavy.point.domain.user.UserReader;

@Component
@RequiredArgsConstructor
public class UserReaderImpl implements UserReader {

	private final UserRepository userRepository;

	@Override public User findUserById(final Long userNo) {
		return null;
	}

	@Override public Long getRemainPoint(final Long userNo) {
		return userRepository.findById(userNo)
				.map(User::getRemainPoint)
				.orElse(0L);
	}

}
