package com.yzp.mybatis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yzp.entity.OrderClothes;
import com.yzp.mybatis.dto.OrderClothesDTO;

import java.util.List;

/**
 * <p>
 * 订单衣物表 服务类
 * </p>
 *
 * @author YangZhiPeng
 * @since 2022-08-29
 */
public interface IOrderClothesService extends IService<OrderClothes> {

    /**
     * 获取衣物某个最新操作集合
     *
     * @param operate
     * @param clothesNumList
     * @return
     */
    List<OrderClothesDTO> getOrderClothesList(Integer operate, List<String> clothesNumList);

}
