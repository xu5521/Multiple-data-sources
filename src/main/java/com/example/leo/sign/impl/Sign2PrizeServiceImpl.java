package com.example.leo.sign.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.leo.entity.CheckConfig;
import com.example.leo.service.CheckConfigService;
import com.example.leo.sign.Sign2PrizeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author leo
 * @version 1.0
 * @desc 优化版
 * @date 2021/12/20 17:07
 */
@Service
@Slf4j
public class Sign2PrizeServiceImpl implements Sign2PrizeService {
    @Autowired
    private CheckConfigService checkConfigService;

    @Override
    public Long amount(int type) {
        CheckConfig checkConfig = checkConfigService.getOne(Wrappers.<CheckConfig>lambdaQuery().eq(CheckConfig::getNum, type));
        return checkConfig.getAmonnt();
    }
}
