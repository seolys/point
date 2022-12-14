package seolnavy.point.domain.user;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserCommand {

	@Getter
	@AllArgsConstructor(staticName = "of")
	public static class UpdatePoint {

		private Long userNo; // 회원번호
		private Long point; // 잔여포인트

		public User toEntity() {
			return User.create(userNo, point);
		}
	}

}
