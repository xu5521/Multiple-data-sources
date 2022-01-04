package com.example.leo.common.lang;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotBlank;

/**
 * @author leo
 * @version 1.0
 * @desc
 * @date 2021/12/20 19:33
 */
@Data
@SuperBuilder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class CheckNumParameter{
    //开始时间,结束时间 数组

    private String[] createTime = new String[2];

    private String username;

    private String startTime;
    private String endTime;


}
