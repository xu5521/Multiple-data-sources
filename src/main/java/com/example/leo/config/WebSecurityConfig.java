package com.example.leo.config;
import com.example.leo.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * @author leo
 * @version 1.0
 * @desc springsecurity 需要处理这个过滤器
 * LogoutFilter - 登出过滤器
 * logoutSuccessHandler - 登出成功之后的操作类
 * UsernamePasswordAuthenticationFilter - from提交用户名密码登录认证过滤器
 * AuthenticationFailureHandler - 登录失败操作类
 * AuthenticationSuccessHandler - 登录成功操作类
 * BasicAuthenticationFilter - Basic身份认证过滤器
 * SecurityContextHolder - 安全上下文静态工具类
 * AuthenticationEntryPoint - 认证失败入口
 * ExceptionTranslationFilter - 异常处理过滤器
 * AccessDeniedHandler - 权限不足操作类
 * FilterSecurityInterceptor - 权限判断拦截器、出口
 * @date 2021/11/1 18:40
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private LoginFailureHandler loginFailureHandler;

    @Autowired
    private CaptchaFilter captchaFilter;

    @Autowired
    private LoginSuccessHandler loginSuccessHandler;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;


    public static final String[] URL_WHITELIST = {
            "/webjars/**",
            "/favicon.ico",
            "/captcha",
            "/login",
            "/logout",
            "/api/desc",
            "/api/test/**",
            "/platform/**",
            "/check-num/**"
    };

    //    @Override
//    protected void configure(HttpSecurity httpSecurity) throws Exception {
//        //添加不认证的URL
//        httpSecurity
//                // CSRF禁用，因为不使用session
//                .csrf().disable()
//                .authorizeRequests()
//                //添加不认证的url
//                .antMatchers("/api/test/**").anonymous()
//                .antMatchers("/api/**").anonymous()
//                // 除上面外的所有请求全部需要鉴权认证
//                .anyRequest().authenticated()
//                .and()
//                .headers().frameOptions().disable();
//
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .formLogin()
                //登录 失败处理
                .failureHandler(loginFailureHandler)
                //登录成功的处理
                .successHandler(loginSuccessHandler)

                .and()
                .authorizeRequests()
                .antMatchers(URL_WHITELIST).permitAll() //白名单
                .anyRequest().authenticated()
                // 不会创建 session
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()

                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .addFilter(jwtAuthenticationFilter())
                //验证码 过滤器在在登录过滤器前
                .addFilterBefore(captchaFilter, UsernamePasswordAuthenticationFilter.class) // 登录验证码校验过滤器

        ;
    }

    @Bean
    JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(authenticationManager());
        return filter;
    }

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
