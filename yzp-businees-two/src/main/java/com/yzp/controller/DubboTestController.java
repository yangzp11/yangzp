package com.yzp.controller;

import com.yzp.api.RemoteOrderService;
import com.yzp.entity.OrderInfo;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * desc
 *
 * @author:YangZhiPeng
 * @date: 2024/9/12 10:04
 */
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class DubboTestController {

	@DubboReference
	private RemoteOrderService remoteOrderService;

	@GetMapping("/query_order")
	public OrderInfo getOrderInfo(@RequestParam("orderId") String orderId) {
		return remoteOrderService.getOrderInfo(orderId);
	}

}
