package com.yzp.mybatis.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yzp.mybatis.dto.UserEventDTO;
import com.yzp.mybatis.entity.TestMsg;
import com.yzp.mybatis.entity.TestUser;
import com.yzp.mybatis.mapper.TestUserMapper;
import com.yzp.mybatis.service.ITestMsgService;
import com.yzp.mybatis.service.ITestUserService;
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
public class TestUserServiceImpl extends ServiceImpl<TestUserMapper, TestUser> implements ITestUserService {

    @Autowired
    private ITestMsgService testMsgService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean updateTestUser(UserEventDTO userEventDTO) {
        log.info("开始更新本地事务，消息ID：{}", userEventDTO.getMsgId());

        //修改余额
        UpdateWrapper<TestUser> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("user_id", userEventDTO.getUserId());
        updateWrapper.setSql("balance = balance + " + userEventDTO.getBalance());
        update(updateWrapper);

        log.info("结束更新本地事务，消息ID：{}", userEventDTO.getMsgId());
        return Boolean.TRUE;
    }
}
