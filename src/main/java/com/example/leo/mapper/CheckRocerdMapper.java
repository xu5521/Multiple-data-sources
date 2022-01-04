package com.example.leo.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.leo.common.dto.CheckRocerdDto;
import com.example.leo.common.lang.CheckNumParameter;
import com.example.leo.entity.CheckRocerd;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.leo.service.CheckRocerdService;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户签到详情记录表 Mapper 接口
 * </p>
 *
 * @author Author：leo
 * @since 2021-12-18
 */
public interface CheckRocerdMapper extends BaseMapper<CheckRocerd> {

    CheckRocerd selectYesterdayClock(@Param("userId") Long userId, @Param("today") String today);

    IPage<CheckRocerdDto> selectListByCreateTimeAndUserId(Page page, @Param("query") CheckNumParameter searchParam);
}
