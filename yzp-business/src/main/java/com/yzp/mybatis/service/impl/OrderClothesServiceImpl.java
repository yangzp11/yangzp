package com.yzp.mybatis.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzp.entity.OrderClothes;
import com.yzp.mybatis.dto.OrderClothesDTO;
import com.yzp.mybatis.mapper.OrderClothesMapper;
import com.yzp.mybatis.service.IOrderClothesService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 订单衣物表 服务实现类
 * </p>
 *
 * @author YangZhiPeng
 * @since 2022-08-29
 */
@Service
public class OrderClothesServiceImpl extends ServiceImpl<OrderClothesMapper, OrderClothes> implements IOrderClothesService {

    @Override
    public List<OrderClothesDTO> getOrderClothesList(Integer operate, List<String> clothesNumList) {
        return baseMapper.getOrderClothesList(operate, clothesNumList);
    }
}
