package ru.inno.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import ru.inno.prop.AppProps;

@Configuration
@EnableConfigurationProperties(value = AppProps.class)
public class AppConfig {
}
