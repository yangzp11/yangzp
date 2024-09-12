package com.yzp.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 订单衣物表
 * </p>
 *
 * @author YangZhiPeng
 * @since 2022-08-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OrderClothes implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
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


}
