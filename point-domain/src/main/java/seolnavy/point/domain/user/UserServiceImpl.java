package seolnavy.point.domain.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seolnavy.point.domain.user.UserCommand.SaveUserPoint;

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
	@Override public void saveUserPoint(final SaveUserPoint savePoint) {
		userStore.saveUserPoint(savePoint.toEntity());
	}

}
