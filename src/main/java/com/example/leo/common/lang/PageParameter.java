package com.example.leo.common.lang;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * @author leo
 * @version 1.0
 * @desc
 * @date 2021/12/20 19:30
 */
@Data
@SuperBuilder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class PageParameter implements Serializable {

    private static final long serialVersionUID = 1L;

    //第几页
    public Long current;
    //每页数量
    public Long size;
}
