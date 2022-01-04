package com.example.leo.common.dto;

import lombok.*;

import java.io.Serializable;

/**
 * @author leo
 * @version 1.0
 * @desc  用户打卡时返回的对象
 * @date 2021/12/20 15:40
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 返回用户连续打卡次数
     */
    private Integer num;

    /**
     * 返回用户奖励金额
     * 乘了 1000
     * 前端要除以 1000
     */
    private Long amount;

}
