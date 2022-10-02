package seolnavy.point.domain.user;

import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seolnavy.point.domain.user.UserCommand.UpdatePoint;
import seolnavy.point.domain.user.UserInfo.Main;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserReader userReader;
	private final UserStore userStore;

	@Override public Long getRemainPoint(final Long userNo) {
		return userReader.getRemainPoint(userNo);
	}

	@Transactional
	@Override public Main increaseUserPoint(final UpdatePoint command) {
		final var userOptional = userReader.findUserById(command.getUserNo());
		if (userOptional.isEmpty()) {
			final var savedUser = userStore.save(command.toEntity());
			return UserInfo.Main.of(savedUser);
		}
		final var findUser = userOptional.get();
		findUser.increasePoint(command.getPoint());
		return UserInfo.Main.of(findUser);
	}

	@Transactional
	@Override public Main decreaseUserPoint(final UpdatePoint command) {
		final var findUser = userReader.findUserById(command.getUserNo()).orElseThrow(EntityNotFoundException::new);
		findUser.decreasePoint(command.getPoint());
		return UserInfo.Main.of(findUser);
	}

}
