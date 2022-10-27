package com.yzp.mybatis.dto;

import lombok.Data;

/**
 * desc
 *
 * @author YangZhiPeng
 * @date 2022/10/27 15:10
 */
@Data
public class ShopEventDTO {

    private Integer shopId;

    private String shopName;

    private Integer stock;

    private String msgId;

}
