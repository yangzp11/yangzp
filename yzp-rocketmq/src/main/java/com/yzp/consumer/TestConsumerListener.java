package com.yzp.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * desc
 *
 * @author YangZhiPeng
 * @date 2022/10/26 16:47
 */
@Component
@RocketMQMessageListener(topic = "first-topic",consumerGroup = "my-consumer-group")
@Slf4j
public class TestConsumerListener implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        log.info("我收到消息了！消息内容为："+message);
    }

}
