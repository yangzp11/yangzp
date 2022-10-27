package com.yzp.consumer;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yzp.mybatis.dto.UserEventDTO;
import com.yzp.mybatis.entity.TestMsg;
import com.yzp.mybatis.mapper.TestMsgMapper;
import com.yzp.mybatis.service.ITestMsgService;
import com.yzp.mybatis.service.ITestUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * desc
 *
 * @author YangZhiPeng
 * @date 2022/10/27 10:20
 */
@Slf4j
@Component
@RocketMQTransactionListener
public class TransactionListenerImpl implements RocketMQLocalTransactionListener {
    private final ITestMsgService testMsgService;
    private final ITestUserService testUserService;

    public TransactionListenerImpl(ITestMsgService testMsgService, ITestUserService testUserService) {
        this.testMsgService = testMsgService;
        this.testUserService = testUserService;
    }

    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        try {
            String body = new String((byte[]) msg.getPayload(), StandardCharsets.UTF_8);
            UserEventDTO userEventDTO = JSONObject.parseObject(body, UserEventDTO.class);
            testUserService.updateTestUser(userEventDTO);
            //保存消息
            TestMsg testMsg = new TestMsg();
            testMsg.setMsgId(userEventDTO.getMsgId());
            testMsg.setMsgContent("msg content");
            testMsgService.saveTestMsg(testMsg);

            return RocketMQLocalTransactionState.COMMIT;
        } catch (Exception ex) {
            log.info("出现异常：{}，进行回滚", ex.getMessage());
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }

    //mq回调检查本地事务执行情况，默认一分钟
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        log.info("checkLocalTransaction===>{}", msg);
        RocketMQLocalTransactionState state;
        String body = new String((byte[]) msg.getPayload(), StandardCharsets.UTF_8);
        UserEventDTO userEventDTO = JSONObject.parseObject(body, UserEventDTO.class);
        //事务id
        String msgId = userEventDTO.getMsgId();
        int isExistMsgId = testMsgService.count(Wrappers.<TestMsg>lambdaQuery().eq(TestMsg::getMsgId, msgId));
        log.info("回查事务，事务号: {} 结果: {}", msgId, isExistMsgId);
        if (isExistMsgId > 0) {
            state = RocketMQLocalTransactionState.COMMIT;
        } else {
            state = RocketMQLocalTransactionState.UNKNOWN;
        }
        return state;
    }
}
