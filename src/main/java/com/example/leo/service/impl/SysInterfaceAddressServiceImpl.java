package com.example.leo.service.impl;

import com.example.leo.entity.SysInterfaceAddress;
import com.example.leo.mapper.SysInterfaceAddressMapper;
import com.example.leo.service.SysInterfaceAddressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 平台充提款接口地址 服务实现类
 * </p>
 *
 * @author Author：leo
 * @since 2021-12-15
 */
@Service
public class SysInterfaceAddressServiceImpl extends ServiceImpl<SysInterfaceAddressMapper, SysInterfaceAddress> implements SysInterfaceAddressService {

    @Override
    public int insertIngoreAdress(List<SysInterfaceAddress> objects) {
        return this.baseMapper.insertIngoreAdress(objects);
    }
}
