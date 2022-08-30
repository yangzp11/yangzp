package com.yzp.mybatis.service.processor.impl;

import cn.hutool.core.util.IdUtil;
import com.yzp.mybatis.entity.OrderInfo;
import com.yzp.mybatis.entity.OrderClothes;
import com.yzp.mybatis.entity.OrderClothesOperate;
import com.yzp.mybatis.entity.OrderClothesRewash;
import com.yzp.mybatis.service.IOrderClothesOperateService;
import com.yzp.mybatis.service.IOrderClothesRewashService;
import com.yzp.mybatis.service.IOrderClothesService;
import com.yzp.mybatis.service.IOrderInforService;
import com.yzp.mybatis.service.processor.OrderProcessService;
import com.yzp.thread.ClothesNumGenerate;
import com.yzp.utils.number.NumberUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * desc
 *
 * @author YangZhiPeng
 * @date 2022/8/29 10:48
 */
@Component
@RequiredArgsConstructor
public class OrderProcessServiceImpl implements OrderProcessService {

    private final IOrderInforService orderService;
    private final IOrderClothesService orderClothesService;
    private final IOrderClothesOperateService orderClothesOperateService;
    private final IOrderClothesRewashService orderClothesRewashService;
    private final ClothesNumGenerate clothesNumGenerate;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void initMysqlData() {
        List<OrderInfo> addOrderList = new ArrayList<>();
        List<OrderClothes> addOrderClothesList = new ArrayList<>();
        List<OrderClothesRewash> rewashList = new ArrayList<>();
        List<OrderClothesOperate> operateList = new ArrayList<>();
        List<String> clothesNumList = clothesNumGenerate.getOutClothesNumberListNew(20, 1);
        AtomicInteger atomicInteger = new AtomicInteger(0);
        for (int i = 0; i < 10; i++) {
            String orderId = IdUtil.getSnowflakeNextIdStr();
            OrderInfo order = new OrderInfo();
            order.setOrderId(orderId);
            order.setOrderNumber(NumberUtils.getThirteenNumber());
            order.setOrderAmount(new BigDecimal("99"));
            addOrderList.add(order);
            for (int j = 0; j < 2; j++) {
                String clothesNum = clothesNumList.get(atomicInteger.get());
                atomicInteger.getAndIncrement();
                OrderClothes orderClothes = new OrderClothes();
                orderClothes.setOrderId(orderId);
                orderClothes.setClothesId(1 + i + j);
                orderClothes.setClothesName("衣物" + i + j);
                orderClothes.setClothesNum(clothesNum);
                orderClothes.setClothesStatus(j);
                addOrderClothesList.add(orderClothes);
                if (1 == j) {
                    OrderClothesRewash orderClothesRewash = new OrderClothesRewash();
                    orderClothesRewash.setClothesNum(clothesNum);
                    orderClothesRewash.setRewashReason("test");
                    rewashList.add(orderClothesRewash);
                }
                OrderClothesOperate orderClothesOperate = new OrderClothesOperate();
                orderClothesOperate.setClothesNum(clothesNum);
                orderClothesOperate.setOperateStatus(1);
                operateList.add(orderClothesOperate);
            }
        }
        orderService.saveBatch(addOrderList);
        orderClothesService.saveBatch(addOrderClothesList);
        orderClothesRewashService.saveBatch(rewashList);
        orderClothesOperateService.saveBatch(operateList);
    }
}
