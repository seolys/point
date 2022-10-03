package seolnavy.point.domain.user.eventlistener;

import lombok.AllArgsConstructor;
import lombok.Getter;
import seolnavy.point.domain.user.User;

@Getter
@AllArgsConstructor(staticName = "of")
public class UserPointUpdateEvent {

	private Long userNo; // 회원번호

	private Long remainPoint; // 잔여포인트

	public static UserPointUpdateEvent of(final User user) {
		return of(user.getUserNo(), user.getRemainPoint());
	}

}
