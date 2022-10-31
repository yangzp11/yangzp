package com.yzp.consumer;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 消费延迟消息
 *
 * @author YangZhiPeng
 * @date 2022/10/27 9:51
 */
@Component
@RocketMQMessageListener(topic = "test-topic-delay", consumerGroup = "test-delay", maxReconsumeTimes = 1)
@Slf4j
public class RocketMQDelayConsumerListener implements RocketMQListener<String> {

    @Override
    @SuppressWarnings("unchecked")
    public void onMessage(String message) {
        Map<String, Object> orderMap = JSONObject.parseObject(message, Map.class);
        String orderNumber = String.valueOf(orderMap.get("orderNumber"));
        String createTime = String.valueOf(orderMap.get("createTime"));
        //根据orderNumber 查询订单状态，若为未支付，则消息订单并修改库存
        //....
        log.info("延时消息消费：{}，消费时间：{}", JSONObject.toJSONString(orderMap), DateUtil.now());
    }
}
