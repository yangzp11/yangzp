package com.yzp.producer;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import com.yzp.mybatis.dto.UserEventDTO;
import com.yzp.mybatis.service.ITestMsgService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * desc
 *
 * @author YangZhiPeng
 * @date 2022/10/26 16:53
 */
@RestController
@RequestMapping("/rocket_mq")
@Slf4j
public class TestProducer {

	private final RocketMQTemplate rocketMQTemplate;
	private final ITestMsgService testMsgService;

	public TestProducer(RocketMQTemplate rocketMQTemplate, ITestMsgService testMsgService) {
		this.rocketMQTemplate = rocketMQTemplate;
		this.testMsgService = testMsgService;
	}

	@GetMapping("/send")
	public void send() {
		rocketMQTemplate.convertAndSend("first-topic", "你好,Java旅途");
	}

	/**
	 * 发送延时消息
	 */
	@GetMapping("/testDelaySend/{delayLevel}")
	public void testDelaySend(@PathVariable("delayLevel") Integer delayLevel) {

		Map<String, Object> orderMap = new HashMap<>();
		orderMap.put("orderNumber", "1357890");
		orderMap.put("createTime", DateUtil.now());

		//参数一：topic   如果想添加tag,可以使用"topic:tag"的写法
		//参数二：Message<?>
		//参数三：消息发送超时时间
		//参数四：delayLevel 延时level  messageDelayLevel=1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
		//参数四 可在broker.conf中修改messageDelayLevel，然后启动时-c指定config启动
		rocketMQTemplate.syncSend("test-topic-delay", MessageBuilder.withPayload(JSONObject.toJSONString(orderMap)).build(),
			3000, delayLevel);
		//5.X可直接指定时间
		rocketMQTemplate.syncSendDelayTimeSeconds("test-topic-dela",
			MessageBuilder.withPayload(JSONObject.toJSONString(orderMap)).build(), 10);
		log.info("延迟消息发送：{}", JSONObject.toJSONString(orderMap));
	}

	/**
	 * 发送事务消息
	 */
	@GetMapping("/sendTransactionStr")
	public void sendTransactionStr() {
		UserEventDTO userEventDTO = new UserEventDTO();
		userEventDTO.setUserId(123456);
		userEventDTO.setBalance(new BigDecimal(10));
		userEventDTO.setMsgId(String.valueOf(IdUtil.getSnowflakeNextId()));
		userEventDTO.setShopId(123456);
		Message<String> message = MessageBuilder.withPayload(JSONObject.toJSONString(userEventDTO)).build();
		TransactionSendResult res = rocketMQTemplate.sendMessageInTransaction("transaction-str:", message, null);
		if (res.getLocalTransactionState().equals(LocalTransactionState.COMMIT_MESSAGE) && res.getSendStatus().equals(SendStatus.SEND_OK)) {
			log.info("事物消息发送成功");
		}
		log.info("事物消息发送结果:{}", res);
	}

}
