package com.yzp.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * desc
 *
 * @author YangZhiPeng
 * @date 2022/8/23 16:21
 */
@Data
public class Test {

    private String testId;

    private BigDecimal testMoney;

    private Integer testType;

    private Integer testStatus;

    private Integer groupIdOne = 1;

    private Integer groupIdTwo = 2;

    private LocalDateTime createdTime;

    private Integer count;

}
