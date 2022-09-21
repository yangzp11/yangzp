package com.yzp.mybatis.controller;


import com.yzp.mybatis.dto.OrderClothesDTO;
import com.yzp.mybatis.entity.OrderClothes;
import com.yzp.mybatis.service.IOrderClothesService;
import com.yzp.mybatis.service.processor.OrderProcessService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 订单衣物表 前端控制器
 * </p>
 *
 * @author YangZhiPeng
 * @since 2022-08-29
 */
@RestController
@RequestMapping("/order_clothes")
@RequiredArgsConstructor
public class OrderClothesController {

    private final OrderProcessService orderProcessService;
    private final IOrderClothesService orderClothesService;

    /**
     * 导出数据
     *
     * @param operate
     * @param response
     * @throws Exception
     */
    @GetMapping("/export")
    public void exportData(Integer operate, HttpServletResponse response) throws Exception {
        orderProcessService.exportData(operate, null, response);
    }

    /**
     * 获取衣物某个最新操作集合
     */
    @GetMapping("/list")
    public List<OrderClothesDTO> exportData() {
        return orderClothesService.getOrderClothesList(1,
                Arrays.asList("10010241", "10010242", "10010245", "10010248", "10010253", "10010254", "10010257"));
    }
}

