package seolnavy.point.domain.user.eventlistener;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import seolnavy.point.domain.user.UserStore;

@Component
@RequiredArgsConstructor
public class UserPointUpdateEventListener {

	private final UserStore userStore;

	@TransactionalEventListener
	public void userPointUpdateEventListener(final UserPointUpdateEvent event) {
		userStore.setUserPointToCache(event.getUserNo(), event.getRemainPoint());
	}

}
