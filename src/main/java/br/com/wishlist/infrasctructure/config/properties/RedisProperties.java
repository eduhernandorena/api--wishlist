package br.com.wishlist.infrasctructure.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("redis")
@Getter
@Setter
public class RedisProperties {

    private String host;
    private int port;
    private String password;
}
