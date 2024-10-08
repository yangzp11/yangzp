package com.yzp.mybatis.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzp.mybatis.dto.UserEventDTO;
import com.yzp.mybatis.entity.TestMsgTwo;
import com.yzp.mybatis.entity.TestShop;
import com.yzp.mybatis.mapper.TestShopMapper;
import com.yzp.mybatis.service.ITestMsgTwoService;
import com.yzp.mybatis.service.ITestShopService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author YangZhiPeng
 * @since 2022-10-27
 */
@Service
@Slf4j
public class TestShopServiceImpl extends ServiceImpl<TestShopMapper, TestShop> implements ITestShopService {

    @Autowired
    private ITestMsgTwoService testMsgTwoService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean updateShop(UserEventDTO shopEventDTO) {
        log.info("开始更新本地事务，消息ID：{}", shopEventDTO.getMsgId());
        //幂等校验
        long isExistMegId = testMsgTwoService.count(Wrappers.<TestMsgTwo>lambdaQuery().eq(TestMsgTwo::getMsgId, shopEventDTO.getMsgId()));
        if (isExistMegId <= 0) {
            UpdateWrapper<TestShop> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("shop_id", shopEventDTO.getShopId());
            updateWrapper.setSql("stock = stock - " + 1);
            update(updateWrapper);
            //保存消息
            TestMsgTwo testMsg = new TestMsgTwo();
            testMsg.setMsgId(shopEventDTO.getMsgId());
            testMsg.setMsgContent("msg content");
            testMsgTwoService.saveTestMsgTwo(testMsg);
            log.info("更新本地事务执行成功，本次消息ID: {}", shopEventDTO.getMsgId());
        } else {
            log.info("更新本地事务执行失败，本次消息ID: {}", shopEventDTO.getMsgId());
        }
        return Boolean.TRUE;
    }

}
