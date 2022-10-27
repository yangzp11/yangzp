package com.yzp.mybatis.service;

import com.yzp.mybatis.dto.UserEventDTO;
import com.yzp.mybatis.entity.TestUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author YangZhiPeng
 * @since 2022-10-27
 */
public interface ITestUserService extends IService<TestUser> {

        Boolean updateTestUser(UserEventDTO userEventDTO);

}
