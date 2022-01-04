package com.example.leo.service;

import com.example.leo.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 我的公众号：MarkerHub
 * @since 2021-11-02
 */
public interface SysUserService extends IService<SysUser> {

    SysUser getByUsername(String username);

    String getUserAuthorityInfo(Long userId);

    void clearUserAuthorityInfo(String username);

    void clearUserAuthorityInfoByRoleId(Long roleId);

    void clearUserAuthorityInfoByMenuId(Long menuId);

}
