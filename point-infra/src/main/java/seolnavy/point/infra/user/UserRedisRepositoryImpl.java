package seolnavy.point.infra.user;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRedisRepositoryImpl implements UserRedisRepository {

	private final RedisTemplate<String, String> remainPointRedisTemplate;

	@Override public void setUserPoint(final Long userNo, final Long remainPoint) {
		remainPointRedisTemplate.opsForValue().set(getUserRemainPointKey(userNo), remainPoint.toString());
	}

	@Override public void increaseUserPoint(final Long userNo, final Long increasePoint) {
		remainPointRedisTemplate.opsForValue().increment(getUserRemainPointKey(userNo), increasePoint);
	}

	@Override public Optional<Long> getUserRemainPoint(final Long userNo) {
		final var remainPoint = remainPointRedisTemplate.opsForValue().get(getUserRemainPointKey(userNo));
		return Optional.ofNullable(remainPoint)
				.map(Long::parseLong);
	}

	private String getUserRemainPointKey(final Long userNo) {
		return String.format("user:%s:point:remain", userNo);
	}

}
