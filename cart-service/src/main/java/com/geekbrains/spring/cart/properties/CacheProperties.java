package com.geekbrains.spring.cart.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "spring.cache.user")
@Data
public class CacheProperties {

    private Long expireTime;

    private String name;

}
