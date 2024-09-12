package com.yzp.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yzp.entity.OrderClothes;
import com.yzp.mybatis.dto.OrderClothesDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 订单衣物表 Mapper 接口
 * </p>
 *
 * @author YangZhiPeng
 * @since 2022-08-29
 */
public interface OrderClothesMapper extends BaseMapper<OrderClothes> {

    /**
     * 获取衣物某个最新操作集合
     *
     * @param operate
     * @param clothesNumList
     * @return
     */
    List<OrderClothesDTO> getOrderClothesList(@Param("operate") Integer operate, @Param("clothesNumList") List<String> clothesNumList);

}
