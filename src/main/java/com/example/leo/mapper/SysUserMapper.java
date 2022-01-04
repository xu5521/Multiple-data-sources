package com.example.leo.mapper;

import com.example.leo.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 我的公众号：MarkerHub
 * @since 2021-11-02
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    List<Long> getNavMenuIds(Long userId);

    List<SysUser> listByMenuId(Long menuId);
}
