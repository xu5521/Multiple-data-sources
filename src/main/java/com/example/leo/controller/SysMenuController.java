package com.example.leo.controller;
import cn.hutool.core.map.MapUtil;
import com.example.leo.common.lang.Result;
import com.example.leo.entity.SysUser;
import com.example.leo.service.SysMenuService;
import com.example.leo.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 我的公众号：MarkerHub
 * @since 2021-11-02
 */
@RestController
@RequestMapping("/sys-menu")
public class SysMenuController extends BaseController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 获取当前用户的菜单栏以及权限
     */
    @GetMapping("/nav")
    public Result nav(Principal principal) {
        String username = principal.getName();
        SysUser sysUser = sysUserService.getByUsername(username);
        // ROLE_Admin,sys:user:save
        String[] authoritys = StringUtils.tokenizeToStringArray(
                sysUserService.getUserAuthorityInfo(sysUser.getId())
                , ",");
        return Result.success(
                MapUtil.builder()
                        .put("nav", sysMenuService.getcurrentUserNav())
                        .put("authoritys", authoritys)
                        .map()
        );
    }

}
