package com.example.tutorial.common.configurations;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.core.convert.RedisCustomConversions;
import org.springframework.beans.factory.annotation.Value;

@Configuration
@EnableCaching
@PropertySources(
    @PropertySource("classpath:application-rediscache.properties")
)
public class RedisConfiguration {

    @Value("${spring.data.redis.host}")
    private String redisHost;

    @Value("${spring.data.redis.port}")
    private int redisPort;

    @Value("${spring.data.redis.username}")
    private String redisUsername;

    @Value("${spring.data.redis.password}")
    private String redisPassword;

    /**
     * Configures a LettuceConnectionFactory for Redis.
     * This factory is used to create connections to the Redis server.
     *
     * @return the configured LettuceConnectionFactory
     */
    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(redisHost);
        config.setPort(redisPort);
        config.setUsername(redisUsername);
        config.setPassword(redisPassword);
        return new LettuceConnectionFactory(config);
    }

    /**
     * Configures a RedisTemplate for Redis operations.
     * This template is used to perform CRUD operations on Redis data.
     *
     * @param connectionFactory the Redis connection factory
     * @return the configured RedisTemplate
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }

    /**
     * Provides a custom RedisCustomConversions bean.
     * This bean can be used to register custom converters for Redis data types.
     *
     * @return the configured RedisCustomConversions
     */
    @Bean(name = "redisCustomConversions")
    @Primary
    public RedisCustomConversions redisCustomConversions() {
        return new RedisCustomConversions(java.util.Collections.emptyList());
    }
}
