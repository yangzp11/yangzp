package com.yzp.consumer;

import com.alibaba.fastjson.JSONObject;
import com.yzp.mybatis.dto.ShopEventDTO;
import com.yzp.mybatis.dto.UserEventDTO;
import com.yzp.mybatis.entity.TestMsg;
import com.yzp.mybatis.service.ITestShopService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * desc
 *
 * @author YangZhiPeng
 * @date 2022/10/27 10:21
 */
@Slf4j
@Component
@RocketMQMessageListener(consumerGroup = "transaction-group", topic = "transaction-str", consumeMode = ConsumeMode.ORDERLY)
public class TransactionConsumer implements RocketMQListener<String> {

    @Autowired
    private ITestShopService testShopService;

    @Override
    public void onMessage(String str) {
        log.info("开始消费消息:{}", str);
        //解析消息为对象
        UserEventDTO shopEventDTO = JSONObject.parseObject(str, UserEventDTO.class);
        //扣减商品库存
        testShopService.updateShop(shopEventDTO);

    }

}
