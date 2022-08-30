package com.yzp.mybatis.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 订单表
 * </p>
 *
 * @author YangZhiPeng
 * @since 2022-08-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OrderInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.INPUT)
    private String orderId;

    private String orderNumber;

    private BigDecimal orderAmount;

    private LocalDateTime createdTime;


}
