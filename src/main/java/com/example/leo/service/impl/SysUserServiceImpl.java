package com.example.leo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.leo.entity.SysMenu;
import com.example.leo.entity.SysRole;
import com.example.leo.entity.SysUser;
import com.example.leo.mapper.SysUserMapper;
import com.example.leo.service.SysMenuService;
import com.example.leo.service.SysRoleService;
import com.example.leo.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.leo.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 我的公众号：MarkerHub
 * @since 2021-11-02
 */
@Service
@Slf4j
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser getByUsername(String username) {
        return this.baseMapper.selectOne(Wrappers.<SysUser>lambdaQuery()
                .eq(SysUser::getUsername, username)
                .eq(SysUser::getStatu, 1));
    }

    @Override
    public String getUserAuthorityInfo(Long userId) {
        SysUser sysUser = this.getById(userId);
        String authority = null;

        if (redisUtil.hasKey("GrantedAuthority:" + sysUser.getUsername())) {
            // 优先从缓存获取
            authority = (String) redisUtil.get("GrantedAuthority:" + sysUser.getUsername());

        } else {
            //查询拥有哪些角色
            List<SysRole> roles = sysRoleService.list(new QueryWrapper<SysRole>()
                    .inSql("id", "select role_id from sys_user_role where user_id = " + userId));
            //查询拥有哪些菜单
            List<Long> menuIds = sysUserMapper.getNavMenuIds(userId);
            // menuIds = [1,2,3,4,5,6]
            List<SysMenu> menus = sysMenuService.listByIds(menuIds);

            String roleNames = roles.stream().map(r -> "ROLE_" + r.getCode()).collect(Collectors.joining(","));
            String permNames = menus.stream().map(m -> m.getPerms()).collect(Collectors.joining(","));

            authority = roleNames.concat(",").concat(permNames);
            log.info("用户ID - {} ---拥有的权限：{}", userId, authority);

            redisUtil.set("GrantedAuthority:" + sysUser.getUsername(), authority, 60 * 60);

        }
        return authority;
    }

    // 删除某个用户的权限信息
    @Override
    public void clearUserAuthorityInfo(String username) {
        redisUtil.del("GrantedAuthority:" + username);
    }

    // 删除所有与该角色关联的用户的权限信息
    @Override
    public void clearUserAuthorityInfoByRoleId(Long roleId) {
        List<SysUser> sysUsers = this.list(new QueryWrapper<SysUser>()
                .inSql("id", "select user_id from sys_user_role where role_id = " + roleId)
        );
        sysUsers.forEach(u -> {
            //删除某个用户的权限信息
            this.clearUserAuthorityInfo(u.getUsername());
        });
    }

    // 删除所有与该菜单关联的所有用户的权限信息
    @Override
    public void clearUserAuthorityInfoByMenuId(Long menuId) {
        List<SysUser> sysUsers = sysUserMapper.listByMenuId(menuId);
        sysUsers.forEach(u -> {
            //删除某个用户的权限信息
            this.clearUserAuthorityInfo(u.getUsername());
        });
    }
}
