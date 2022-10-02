package seolnavy.point.interfaces.controller.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GetUserRemainPointDto {

	@Getter
	@AllArgsConstructor(staticName = "of")
	public static class Response {

		private long remainPoint;

	}
}
