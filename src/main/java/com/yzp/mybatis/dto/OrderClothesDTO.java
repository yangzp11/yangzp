package com.yzp.mybatis.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 订单衣物DTO
 *
 * @author YangZhiPeng
 * @date 2022/8/30 13:10
 */
@Data
public class OrderClothesDTO {

    /**
     * 订单衣物主键
     */
    private String orderClothesId;

    /**
     * 衣物ID
     */
    private Integer clothesId;

    /**
     * 衣物号
     */
    private String clothesNum;

    /**
     * 衣物状态
     */
    private Integer clothesStatus;

    /**
     * 衣物名称
     */
    private String clothesName;

    /**
     * 订单主键
     */
    private String orderId;

    /**
     * 最新反洗时间
     */
    private LocalDateTime rewashTime;

    /**
     * 操作
     */
    private Integer operate;

    /**
     * 最新操作时间
     */
    private LocalDateTime operateTime;
}
