package com.yzp.mybatis.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yzp.mybatis.dto.ShopEventDTO;
import com.yzp.mybatis.dto.UserEventDTO;
import com.yzp.mybatis.entity.TestMsg;
import com.yzp.mybatis.entity.TestShop;
import com.yzp.mybatis.entity.TestUser;
import com.yzp.mybatis.mapper.TestShopMapper;
import com.yzp.mybatis.service.ITestMsgService;
import com.yzp.mybatis.service.ITestShopService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
    private ITestMsgService testMsgService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean updateShop(UserEventDTO shopEventDTO) {
        log.info("开始更新本地事务，消息ID：{}", shopEventDTO.getMsgId());
        //幂等校验
        int isExistMegId = testMsgService.count(Wrappers.<TestMsg>lambdaQuery().eq(TestMsg::getMsgId, shopEventDTO.getMsgId()));
        if (isExistMegId <= 0) {
            UpdateWrapper<TestShop> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("shop_id", shopEventDTO.getShopId());
            updateWrapper.setSql("stock = stock - " + 1);
            update(updateWrapper);
            //保存消息
            TestMsg testMsg = new TestMsg();
            testMsg.setMsgId(shopEventDTO.getMsgId());
            testMsg.setMsgContent("msg content");
            testMsgService.saveTestMsg(testMsg);

            log.info("更新本地事务执行成功，本次消息ID: {}", shopEventDTO.getMsgId());
        } else {
            log.info("更新本地事务执行失败，本次消息ID: {}", shopEventDTO.getMsgId());
        }
        return Boolean.TRUE;
    }

}
