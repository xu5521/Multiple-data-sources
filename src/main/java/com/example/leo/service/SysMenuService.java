package com.example.leo.service;

import com.example.leo.common.dto.SysMenuDto;
import com.example.leo.entity.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 我的公众号：MarkerHub
 * @since 2021-11-02
 */
public interface SysMenuService extends IService<SysMenu> {

    //获取菜单权限
    List<SysMenuDto> getcurrentUserNav();
}
