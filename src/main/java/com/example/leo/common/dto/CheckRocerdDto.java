package com.example.leo.common.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * @author leo
 * @version 1.0
 * @desc
 * @date 2021/12/21 17:31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CheckRocerdDto implements Serializable {

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private String createTime;

    private String username;
}
