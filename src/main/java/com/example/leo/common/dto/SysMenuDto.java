package com.example.leo.common.dto;
import lombok.Data;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author leo
 * @version 1.0
 * @desc 菜单展示dto
 * @date 2021/11/2 18:53
 */
@Data
public class SysMenuDto implements Serializable {

    private Long id;
    private String title;
    private String icon;
    private String path;
    private String name;
    private String component;
    List<SysMenuDto> children = new ArrayList<>();

}