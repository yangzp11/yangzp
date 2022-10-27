package com.yzp.mybatis.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author YangZhiPeng
 * @since 2022-10-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TestShop implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer shopId;

    private String shopName;

    private Integer stock;


}
