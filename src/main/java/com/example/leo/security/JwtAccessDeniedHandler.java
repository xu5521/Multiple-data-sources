package com.example.leo.security;

import cn.hutool.json.JSONUtil;
import com.example.leo.common.lang.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author leo
 * @version 1.0
 * @desc 无权限数据返回
 * @date 2021/11/2 18:41
 */
@Slf4j
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException, ServletException {
//    response.sendError(HttpServletResponse.SC_FORBIDDEN, accessDeniedException.getMessage());
        log.info("权限不够！！");
        response.setContentType("application/json;charset=UTF-8");
        //403
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        ServletOutputStream outputStream = response.getOutputStream();
        Result result = Result.fail(accessDeniedException.getMessage());
        outputStream.write(JSONUtil.toJsonStr(result).getBytes("UTF-8"));
        outputStream.flush();
        outputStream.close();
    }
}
