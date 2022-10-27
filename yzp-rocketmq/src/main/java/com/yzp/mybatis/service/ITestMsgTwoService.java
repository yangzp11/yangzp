package com.yzp.mybatis.service;

import com.yzp.mybatis.entity.TestMsg;
import com.yzp.mybatis.entity.TestMsgTwo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author YangZhiPeng
 * @since 2022-10-27
 */
public interface ITestMsgTwoService extends IService<TestMsgTwo> {

    Boolean saveTestMsgTwo(TestMsgTwo testMsg);
}
