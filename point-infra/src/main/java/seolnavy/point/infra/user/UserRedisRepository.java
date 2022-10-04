package seolnavy.point.infra.user;

import java.util.Optional;

public interface UserRedisRepository {

	void setUserPoint(Long userNo, Long remainPoint);

	void increaseUserPoint(Long userNo, Long increasePoint);

	Optional<Long> getUserRemainPoint(Long userNo);

}
