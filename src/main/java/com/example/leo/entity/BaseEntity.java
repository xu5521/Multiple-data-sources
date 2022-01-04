package com.example.leo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author leo
 * @version 1.0
 * @desc
 * @date 2021/11/2 12:30
 */
@Data
@SuperBuilder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private LocalDateTime created;
    private LocalDateTime updated;
    private Integer statu;
}
