package com.ktdsuniversity.edu.commons.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.ktdsuniversity.edu.commons.beans.interceptors.CheckSessionInterceptor;

/**
 * 인터셉터 등록을 위한 Spring Boot 설정 클래스
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new CheckSessionInterceptor())
        // 모든 URL에서 인터셉터 추가
        .addPathPatterns("/**")
        // 제외할 URL 가변 길이 인자로 추가
        .excludePathPatterns( //
            "/members/login", //
            "/members/signup", //
            "/articles/list", //
            "/articles/*", //
            "/articles/*/replies/list" //
        );

  }
}
