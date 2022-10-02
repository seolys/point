package seolnavy.point.domain.user;

import java.util.Optional;

public interface UserReader {

	Optional<User> findUserById(Long userNo);

	Long getRemainPoint(Long userNo);

}
