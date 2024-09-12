package com.yzp.api;

import com.yzp.entity.OrderInfo;

/**
 * desc
 *
 * @author:YangZhiPeng
 * @date: 2024/9/6 13:52
 */
public interface RemoteOrderService {

		OrderInfo getOrderInfo(String orderId);
}
