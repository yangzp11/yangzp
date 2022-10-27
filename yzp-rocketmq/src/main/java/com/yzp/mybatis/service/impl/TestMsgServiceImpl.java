package com.yzp.mybatis.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yzp.mybatis.entity.TestMsg;
import com.yzp.mybatis.mapper.TestMsgMapper;
import com.yzp.mybatis.service.ITestMsgService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
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
public class TestMsgServiceImpl extends ServiceImpl<TestMsgMapper, TestMsg> implements ITestMsgService {

    @Override
    public Boolean saveTestMsg(TestMsg testMsg) {
        return save(testMsg);
    }
}
