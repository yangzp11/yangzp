package com.yzp;

import com.yzp.mybatis.dto.OrderClothesDTO;
import com.yzp.mybatis.entity.OrderClothes;
import com.yzp.mybatis.service.IOrderClothesService;
import com.yzp.mybatis.service.processor.OrderProcessService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * desc
 *
 * @author YangZhiPeng
 * @date 2022/8/29 11:06
 */
@SpringBootTest
public class TestSql {

    @Autowired
    private OrderProcessService orderProcessService;
    @Autowired
    private IOrderClothesService orderClothesService;

    @Test
    public void initMysqlData() {
        orderProcessService.initMysqlData();
    }

    @Test
    public void getNewestData() {
        List<OrderClothesDTO> orderClothesDTOList = orderClothesService.getOrderClothesList(1,
                Arrays.asList("10010241","10010242","10010245","10010248","10010253","10010254","10010257"));
        orderClothesDTOList.forEach(System.out::println);
    }
}
