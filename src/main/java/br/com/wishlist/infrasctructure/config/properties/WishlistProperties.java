package br.com.wishlist.infrasctructure.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("wishlist")
@Getter
@Setter
public class WishlistProperties {

    private Integer limitSize;
}
