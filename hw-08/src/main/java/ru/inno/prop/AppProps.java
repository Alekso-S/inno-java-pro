package ru.inno.prop;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.math.BigDecimal;

@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
@RequiredArgsConstructor
@Getter
public class AppProps {
    private final BigDecimal defaultLimit;
}
