package com.example.leo.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.leo.common.dto.CheckRocerdDto;
import com.example.leo.common.lang.CheckNumParameter;
import com.example.leo.entity.CheckRocerd;
import com.example.leo.mapper.CheckRocerdMapper;
import com.example.leo.service.CheckRocerdService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户签到详情记录表 服务实现类
 * </p>
 *
 * @author Author：leo
 * @since 2021-12-18
 */
@Service
public class CheckRocerdServiceImpl extends ServiceImpl<CheckRocerdMapper, CheckRocerd> implements CheckRocerdService {

    @Override
    public CheckRocerd selectYesterdayClock(Long userId, String today) {
        return this.baseMapper.selectYesterdayClock(userId, today);
    }

    @Override
    public IPage<CheckRocerdDto> findByPage(Page buildPage, CheckNumParameter searchParam) {
        return this.baseMapper.selectListByCreateTimeAndUserId(buildPage, searchParam);
    }
}
