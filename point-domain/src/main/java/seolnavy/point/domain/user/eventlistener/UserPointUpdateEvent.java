package seolnavy.point.domain.user.eventlistener;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class UserPointUpdateEvent {

	private Long userNo; // 회원번호

	private Long increasePoint; // 잔여포인트

}
