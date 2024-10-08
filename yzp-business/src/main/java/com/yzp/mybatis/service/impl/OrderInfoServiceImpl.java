package com.yzp.mybatis.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzp.entity.OrderInfo;
import com.yzp.mybatis.mapper.OrderMapper;
import com.yzp.mybatis.service.IOrderInfoService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author YangZhiPeng
 * @since 2022-08-29
 */
@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderMapper, OrderInfo> implements IOrderInfoService {

}
