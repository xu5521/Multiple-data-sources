package com.example.leo.controller;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.example.leo.common.lang.Result;
import com.example.leo.entity.PlatformInterfaceAddress;
import com.example.leo.entity.SysInterfaceAddress;
import com.example.leo.entity.SysUser;
import com.example.leo.service.PlatformInterfaceAddressService;
import com.example.leo.service.SysInterfaceAddressService;
import com.example.leo.service.SysUserService;
import com.example.leo.util.RedisUtil;
import io.lettuce.core.dynamic.domain.Timeout;
import org.redisson.api.RList;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * <p>
 * 平台充提款接口地址 前端控制器
 * </p>
 *
 * @author Author：leo
 * @since 2021-12-15
 */
@RestController
@RequestMapping("/platform")
public class PlatformInterfaceAddressController extends BaseController {

    @Autowired
    private PlatformInterfaceAddressService platformInterfaceAddressService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysInterfaceAddressService sysInterfaceAddressService;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/test")
    public Result test() {
        List<PlatformInterfaceAddress> list = platformInterfaceAddressService.list(null);
        return Result.success(list);
    }

    @GetMapping("/test2")
    public Result test2() throws InterruptedException {
        List<PlatformInterfaceAddress> addresses = redisTemplate.opsForList().range("addresses", 0, -1);
        if (CollectionUtils.isNotEmpty(addresses)){
            return Result.success(addresses);
        }
        addresses = platformInterfaceAddressService.list(null);
        redisTemplate.opsForList().leftPush("addresses",  addresses);
        TimeUnit.SECONDS.sleep(2);
        addresses = redisTemplate.opsForList().range("addresses", 0, -1);
        return Result.success(addresses);
    }

    @GetMapping("/test3")
    public Result test3() {
        RList<SysUser> list = redissonClient.getList("user:list");
        if (CollectionUtils.isEmpty(list)) {
            List<SysUser> sysUser = sysUserService.list(null);
            list.addAll(sysUser);
        }
        return Result.success(list);
    }

    @GetMapping("/sync")
    public Result sync() {
        List<PlatformInterfaceAddress> list = platformInterfaceAddressService.list(null);
        List<SysInterfaceAddress> objects = new ArrayList<>();
        list.stream().forEach(l -> {
            String platformName = l.getPlatformName();
            String platformAddress = l.getPlatformAddress();
            String platformType = l.getPlatformType();
            objects.add(new SysInterfaceAddress().setPlatformName(platformName)
                    .setPlatformAddress(platformAddress)
                    .setPlatformType(platformType));
        });
        return Result.success(sysInterfaceAddressService.insertIngoreAdress(objects));
    }

    /**
     * 同步两个数据库表名一样的
     *
     * @return
     */
    @GetMapping("/sync2")
    public Result sync2() {
        List<PlatformInterfaceAddress> list = platformInterfaceAddressService.list(null);
        List<PlatformInterfaceAddress> objects = new ArrayList<>();
        list.stream().forEach(l -> {
            String platformName = l.getPlatformName();
            String platformAddress = l.getPlatformAddress();
            String platformType = l.getPlatformType();
            objects.add(new PlatformInterfaceAddress().setPlatformName(platformName)
                    .setPlatformAddress(platformAddress)
                    .setPlatformType(platformType));
        });
        return Result.success(platformInterfaceAddressService.insertIngoreAdress(objects));
    }
}
