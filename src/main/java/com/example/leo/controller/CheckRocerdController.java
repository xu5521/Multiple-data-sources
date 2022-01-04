package com.example.leo.controller;


import com.example.leo.common.lang.Result;
import com.example.leo.entity.CheckRocerd;
import com.example.leo.service.CheckConfigService;
import com.example.leo.service.CheckRocerdService;
import com.example.leo.service.SysWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * <p>
 * 用户签到详情记录表 前端控制器
 * </p>
 *
 * @author Author：leo
 * @since 2021-12-18
 */
@RestController
@RequestMapping("/check-rocerd")
public class CheckRocerdController {
    @Autowired
    private CheckConfigService checkConfigService;

    @Autowired
    private CheckRocerdService checkRocerdService;

    @Autowired
    private SysWalletService sysWalletService;


}
