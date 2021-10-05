package com.map.map.config

import com.map.map.filter.JwtAuthenticationInterceptor
import com.map.map.global.GlobalExceptionHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.security.servlet.PathRequest
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@Configuration
@EnableWebSecurity
class SecurityConfig : WebSecurityConfigurerAdapter() {
    @Autowired
    private var globalExceptionHandler : GlobalExceptionHandler? = null
    @Autowired
    private var jwtFilter : JwtAuthenticationInterceptor? = null
    override fun configure(web: WebSecurity) {
        web.ignoring()
            .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
    }

    override fun configure(http: HttpSecurity?){
        http!!
            .httpBasic().disable()
            .cors()
            .and()
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/file","file/**").permitAll()
            .antMatchers("/auth","auth/**").permitAll()
            .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/user","/user/**").permitAll()
                .antMatchers("/album","album/**").permitAll()
            .and()
    }
}