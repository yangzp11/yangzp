package com.yzp.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 订单衣物反洗表
 * </p>
 *
 * @author YangZhiPeng
 * @since 2022-08-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OrderClothesRewash implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    private String rewashId;

    private String clothesNum;

    private String rewashReason;

    private LocalDateTime createdTime;


}
