package com.example.leo.entity;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

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
public class SysUser  implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String username;

    private String password;

    private String avatar;

    private String email;

    private String city;

    private String statu;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastLogin;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date created;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date updated;

    private int checkNum;


}
