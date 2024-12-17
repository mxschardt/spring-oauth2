package com.mxschardt.resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.logbook.BodyFilter;
import org.zalando.logbook.HeaderFilter;
import org.zalando.logbook.HttpHeaders;

import javax.annotation.Nullable;

// Конфигурация для Logbook, чтобы убрать фильтры с заголовков.
// (не нашел как сделать через application.yaml)
@Configuration
public class LoggingConfig {

    private class NoneFilter implements BodyFilter, HeaderFilter{
        @Override
        public String filter(@Nullable String contentType, String body) {
            return body;
        }

        @Override
        public HttpHeaders filter(HttpHeaders headers) {
            return headers;
        }
    }

    private NoneFilter noneFilter = new NoneFilter();

    @Bean
    public BodyFilter bodyFilter() {
        return this.noneFilter;
    }

    @Bean
    public HeaderFilter headerFilter() {
        return this.noneFilter;
    }

}
