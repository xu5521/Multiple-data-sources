package com.example.leo.mapper;


import com.example.leo.entity.SysInterfaceAddress;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface SysInterfaceAddressMapper extends BaseMapper<SysInterfaceAddress> {

    int insertIngoreAdress(@Param("list") List<SysInterfaceAddress> list);
}
