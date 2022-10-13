package seolnavy.point.common.config.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Slf4j
@RequiredArgsConstructor
@Configuration
@EnableRedisRepositories
public class RedisConfig {

	private final RedisProperties redisProperties;

	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		final RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
		redisConfig.setHostName(redisProperties.getHost());
		redisConfig.setPort(redisProperties.getPort());

		return new LettuceConnectionFactory(redisConfig);
	}

	@Bean
	public RedisTemplate<String, Integer> remainPointRedisTemplate() {
		final RedisSerializer<String> stringSerializer = new StringRedisSerializer();
		final RedisTemplate<String, Integer> template = new RedisTemplate<>();
		template.setConnectionFactory(redisConnectionFactory());
		template.setKeySerializer(stringSerializer);
		template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
		return template;
	}
}
