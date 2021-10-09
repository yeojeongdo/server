package com.map.map.config

import com.map.map.filter.JwtAuthenticationInterceptor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class JwtInterceptorConfig @Autowired constructor(
    private var jwtAuthenticationFilter: JwtAuthenticationInterceptor
): WebMvcConfigurer{
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(jwtAuthenticationFilter)
            .addPathPatterns("/user")
            .addPathPatterns("/user/**")
            .addPathPatterns("/album")
            .addPathPatterns("/album/**")
            .addPathPatterns("/comment")
    }

}