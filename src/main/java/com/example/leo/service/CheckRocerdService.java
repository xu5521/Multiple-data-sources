package com.example.leo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.leo.common.dto.CheckRocerdDto;
import com.example.leo.common.lang.CheckNumParameter;
import com.example.leo.entity.CheckRocerd;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户签到详情记录表 服务类
 * </p>
 *
 * @author Author：leo
 * @since 2021-12-18
 */
public interface CheckRocerdService extends IService<CheckRocerd> {

    CheckRocerd selectYesterdayClock(Long userId, String today);

    IPage<CheckRocerdDto> findByPage(Page buildPage, CheckNumParameter searchParam);
}
