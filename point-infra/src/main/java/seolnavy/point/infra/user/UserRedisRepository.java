package seolnavy.point.infra.user;

import java.util.Optional;

public interface UserRedisRepository {

	void saveRemainPoint(Long userNo, Long remainPoint);

	Optional<Long> getUserRemainPoint(Long userNo);

	boolean existsRemainPointKey(Long userNo);
}
