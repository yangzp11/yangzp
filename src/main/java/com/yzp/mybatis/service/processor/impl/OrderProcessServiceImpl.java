package com.yzp.mybatis.service.processor.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.yzp.mybatis.dto.OrderClothesDTO;
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
import com.yzp.utils.excel.ExcelUtil;
import com.yzp.utils.excel.MonthSheetWriteHandler;
import com.yzp.utils.excel.pojo.OrderClothesExcel;
import com.yzp.utils.number.NumberUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

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

    @Override
    public void exportData(Integer operate, List<String> clothesNumList, HttpServletResponse response) throws Exception {
        List<OrderClothesDTO> orderClothesList = orderClothesService.getOrderClothesList(operate, clothesNumList);
        if (CollUtil.isEmpty(orderClothesList)) {
            return;
        }
        AtomicLong atomicLong = new AtomicLong(1);
        String buffer = "导出数据";
        List<OrderClothesExcel> orderClothesExcelList = orderClothesList.stream().map(item -> {
            OrderClothesExcel orderClothesExcel = new OrderClothesExcel();
            BeanUtil.copyProperties(item, orderClothesExcel);
            orderClothesExcel.setSerialNumber(atomicLong.getAndIncrement());
            orderClothesExcel.setRewashTime(LocalDateTimeUtil.format(item.getRewashTime(), DatePattern.NORM_DATETIME_MINUTE_FORMATTER));
            orderClothesExcel.setOperateTime(LocalDateTimeUtil.format(item.getOperateTime(), DatePattern.NORM_DATETIME_MINUTE_FORMATTER));
            return orderClothesExcel;
        }).collect(Collectors.toList());

        String fileName = "导出数据";
        ExcelWriter excelWriter = null;
        try {
            WriteSheet writeSheet = EasyExcel.writerSheet("导出数据").useDefaultStyle(true).relativeHeadRowIndex(1).build();
            excelWriter = ExcelUtil.writeExcel(response, OrderClothesExcel.class, fileName, new MonthSheetWriteHandler(0, 0, 0, 9, buffer));
            excelWriter.write(orderClothesExcelList, writeSheet);
        } finally {
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }
    }
}
