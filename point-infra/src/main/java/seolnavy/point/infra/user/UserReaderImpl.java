package seolnavy.point.infra.user;

import org.springframework.stereotype.Component;
import seolnavy.point.domain.user.User;
import seolnavy.point.domain.user.UserReader;

@Component
public class UserReaderImpl implements UserReader {

	@Override public User findUserById(final Long userNo) {
		return null;
	}

	@Override public Long getRemainPoint(final Long userNo) {
		return null;
	}
}
