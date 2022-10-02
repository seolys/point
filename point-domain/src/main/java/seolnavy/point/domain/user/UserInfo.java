package seolnavy.point.domain.user;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserInfo {

	@Getter
	@AllArgsConstructor(staticName = "of")
	public static class Main {

		private Long userNo; // 회원번호

		private Long remainPoint; // 잔여포인트

		public static Main of(final User savedUser) {
			return Main.of(savedUser.getUserNo(), savedUser.getRemainPoint());
		}
	}

}
