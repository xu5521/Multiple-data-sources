package com.example.leo.service;

import com.example.leo.entity.PlatformInterfaceAddress;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 平台充提款接口地址 服务类
 * </p>
 *
 * @author Author：leo
 * @since 2021-12-15
 */
public interface PlatformInterfaceAddressService extends IService<PlatformInterfaceAddress> {

    int insertIngoreAdress(List<PlatformInterfaceAddress> objects);
}
