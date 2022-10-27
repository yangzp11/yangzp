package com.yzp.mybatis.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * desc
 *
 * @author YangZhiPeng
 * @date 2022/10/27 14:59
 */
@Data
public class UserEventDTO {

    private Integer userId;

    private BigDecimal balance;

    private String msgId;

    private Integer shopId;

}
