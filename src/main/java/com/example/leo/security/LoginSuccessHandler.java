package com.example.leo.security;

import cn.hutool.json.JSONUtil;
import com.example.leo.common.lang.Result;
import com.example.leo.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author leo
 * @version 1.0
 * @desc  登录成功的处理
 * @date 2021/11/2 15:08
 */
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream outputStream = response.getOutputStream();

        // 生成jwt返回
        String jwt = jwtUtils.generateToken(authentication.getName());
        //Authorization
        response.setHeader(jwtUtils.getHeader(), jwt);

        Result result = Result.success(jwt);
        outputStream.write(JSONUtil.toJsonStr(result).getBytes("UTF-8"));
        outputStream.flush();
        outputStream.close();
    }
}
