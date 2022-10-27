package com.yzp.mybatis.service.impl;

import com.yzp.mybatis.entity.TestMsgTwo;
import com.yzp.mybatis.mapper.TestMsgTwoMapper;
import com.yzp.mybatis.service.ITestMsgTwoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author YangZhiPeng
 * @since 2022-10-27
 */
@Service
public class TestMsgTwoServiceImpl extends ServiceImpl<TestMsgTwoMapper, TestMsgTwo> implements ITestMsgTwoService {

    @Override
    public Boolean saveTestMsgTwo(TestMsgTwo testMsg) {
        return save(testMsg);
    }
}
