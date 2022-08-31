package com.yzp.mybatis.controller;


import com.yzp.mybatis.service.processor.OrderProcessService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 订单衣物表 前端控制器
 * </p>
 *
 * @author YangZhiPeng
 * @since 2022-08-29
 */
@RestController
@RequestMapping("/orderClothes")
@RequiredArgsConstructor
public class OrderClothesController {

    private final OrderProcessService orderProcessService;

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
}

