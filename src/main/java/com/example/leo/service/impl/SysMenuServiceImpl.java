package com.example.leo.service.impl;

import com.example.leo.common.dto.SysMenuDto;
import com.example.leo.entity.SysMenu;
import com.example.leo.entity.SysUser;
import com.example.leo.mapper.SysMenuMapper;
import com.example.leo.mapper.SysUserMapper;
import com.example.leo.service.SysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.leo.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 我的公众号：MarkerHub
 * @since 2021-11-02
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysUserMapper sysUserMapper;

    /**
     * 获取当前用户菜单导航
     */
    @Override
    public List<SysMenuDto> getcurrentUserNav() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        SysUser sysUser = sysUserService.getByUsername(username);

        // 获取用户的所有菜单
        List<Long> menuIds = sysUserMapper.getNavMenuIds(sysUser.getId());

        List<SysMenu> menus = buildTreeMenu(this.listByIds(menuIds));
        return convert(menus);
    }

    /**
     * 把list转成树形结构的数据
     */
    private List<SysMenu> buildTreeMenu(List<SysMenu> menus){
        List<SysMenu> finalMenus = new ArrayList<>();
        for (SysMenu menu : menus) {

            // 先寻找各自的孩子
            for (SysMenu e : menus) {
                if (e.getParentId() == menu.getId()) {
                    menu.getChildren().add(e);
                }
            }
            // 提取出父节点
            if (menu.getParentId() == 0L) {
                finalMenus.add(menu);
            }
        }
        return finalMenus;
    }

    /**
     * menu转menuDto
     */
    private List<SysMenuDto> convert(List<SysMenu> menus) {
        List<SysMenuDto> menuDtos = new ArrayList<>();
        menus.forEach(m -> {
            SysMenuDto dto = new SysMenuDto();
            dto.setId(m.getId());
            dto.setName(m.getPerms());
            dto.setTitle(m.getName());
            dto.setComponent(m.getComponent());
            dto.setIcon(m.getIcon());
            dto.setPath(m.getPath());
            if (m.getChildren().size() > 0) {
                dto.setChildren(convert(m.getChildren()));
            }
            menuDtos.add(dto);
        });
        return menuDtos;
    }
}
