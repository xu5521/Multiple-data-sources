package com.example.leo.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.leo.entity.PlatformInterfaceAddress;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 平台充提款接口地址 Mapper 接口
 * </p>
 *
 * @author Author：leo
 * @since 2021-12-15
 */
public interface PlatformInterfaceAddressDb1Mapper extends BaseMapper<PlatformInterfaceAddress> {

    int insertIngoerAdress2(@Param("list") List<PlatformInterfaceAddress> list);
}
