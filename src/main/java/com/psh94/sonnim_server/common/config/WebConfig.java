package com.psh94.sonnim_server.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
@EnableJpaRepositories(basePackages = {"com.psh94.sonnim_server.domain.*.repository"})
public class WebConfig implements WebMvcConfigurer {
}