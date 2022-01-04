package com.example.leo.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author 我的公众号：MarkerHub
 * @since 2021-11-02
 */
@Data
@SuperBuilder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class SysMenu  {

    private static final long serialVersionUID = 1L;

    //菜单集合
    @TableField(exist = false)
    private List<SysMenu> children = new ArrayList<>();

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 父菜单ID，一级菜单为0
     */
    private Long parentId;

    private String name;

    /**
     * 菜单URL
     */
    private String path;

    /**
     * 授权(多个用逗号分隔，如：user:list,user:create)
     */
    private String perms;

    private String component;

    /**
     * 类型     0：目录   1：菜单   2：按钮
     */
    private Integer type;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 排序
     */
    @TableField("orderNum")
    private Integer ordernum;


}
