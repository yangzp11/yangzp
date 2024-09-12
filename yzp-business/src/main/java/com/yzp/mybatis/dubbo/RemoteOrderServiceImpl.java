package com.yzp.mybatis.dubbo;

import com.yzp.api.RemoteOrderService;
import com.yzp.entity.OrderInfo;
import com.yzp.mybatis.service.IOrderInfoService;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

/**
 * desc
 *
 * @author:YangZhiPeng
 * @date: 2024/9/6 14:03
 */
@RequiredArgsConstructor
@Service
@DubboService
public class RemoteOrderServiceImpl implements RemoteOrderService {

	private final IOrderInfoService orderService;

	@Override
	public OrderInfo getOrderInfo(String orderId) {
		return orderService.getById(orderId);
	}

}
