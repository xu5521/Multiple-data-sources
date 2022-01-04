package com.example.leo.security;

import cn.hutool.json.JSONUtil;
import com.example.leo.common.lang.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author leo
 * @version 1.0
 * @desc 定义认证失败处理类
 * @date 2021/11/2 15:44
 */
@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException {
        log.info("认证失败！未登录！");
        response.setContentType("application/json;charset=UTF-8");
        //401
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        ServletOutputStream outputStream = response.getOutputStream();

        Result result = Result.fail("请先登录！");
        outputStream.write(JSONUtil.toJsonStr(result).getBytes("UTF-8"));
        outputStream.flush();
        outputStream.close();
    }
}
