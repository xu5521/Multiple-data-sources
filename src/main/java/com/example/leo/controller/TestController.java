package com.example.leo.controller;
import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.example.leo.common.lang.Result;
import com.example.leo.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author leo
 * @version 1.0
 * @desc
 * @date 2021/11/1 18:41
 */
@RestController
@RequestMapping("/api")
@Slf4j
public class TestController {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private SysUserService sysUserService;

    @GetMapping("/desc")
    public String test() {
        log.info("这是测试");
        return "Hello SpringSecurity";
    }

    @GetMapping("/test/list")
    public Result list() {
        log.info("测试数据" + JSONUtil.toJsonStr(sysUserService.list()));
        return Result.success(sysUserService.list());
    }


    @GetMapping("/test/pass")
    public Result passEncode() {
        // 密码加密
        String pass = bCryptPasswordEncoder.encode("123456");
        // 密码验证
        boolean matches = bCryptPasswordEncoder.matches("123456", pass);
        return Result.success(MapUtil.builder()
                .put("pass", pass)
                .put("marches", matches)
                .build()
        );
    }

    @GetMapping("/less")
    public Result less() {
        return Result.success(MapUtil.builder()
                .put("是否", "是")
                .put("认证", "通过")
                .build()
        );
    }

    public static void main(String[] args) {
        String aa = "aa_bb";
        String trim = aa.trim();
        System.out.println(trim);
    }



}

