package com.example.leo.sign.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.leo.entity.CheckConfig;
import com.example.leo.service.CheckConfigService;
import com.example.leo.sign.SignPrizeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author leo
 * @version 1.0
 * @desc
 * @date 2021/12/20 17:07
 */
@Service("5SignPrizeService")
@Slf4j
public class FiveSignPrizeService implements SignPrizeService {
    @Autowired
    private CheckConfigService checkConfigService;

    @Override
    public Long amount() {
        CheckConfig checkConfig = checkConfigService.getOne(Wrappers.<CheckConfig>lambdaQuery().eq(CheckConfig::getNum, 5));
        return checkConfig.getAmonnt();
    }
}
