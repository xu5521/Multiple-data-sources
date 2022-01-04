package com.example.leo.service.impl;

import com.example.leo.entity.PlatformInterfaceAddress;
import com.example.leo.mapper.PlatformInterfaceAddressDb1Mapper;
import com.example.leo.mapper.PlatformInterfaceAddressMapper;
import com.example.leo.service.PlatformInterfaceAddressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.ws.soap.Addressing;
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
public class PlatformInterfaceAddressServiceImpl extends ServiceImpl<PlatformInterfaceAddressMapper, PlatformInterfaceAddress> implements PlatformInterfaceAddressService {
    @Autowired
    private PlatformInterfaceAddressDb1Mapper platformInterfaceAddressDb1Mapper;


    @Override
    public int insertIngoreAdress(List<PlatformInterfaceAddress> objects) {
        return platformInterfaceAddressDb1Mapper.insertIngoerAdress2(objects);
    }
}
