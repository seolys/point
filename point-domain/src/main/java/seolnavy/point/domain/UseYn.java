package seolnavy.point.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UseYn {
	Y(true), N(false);

	private final boolean isTrue;

}
