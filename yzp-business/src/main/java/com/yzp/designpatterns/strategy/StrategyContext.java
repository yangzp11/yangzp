package com.yzp.designpatterns.strategy;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author:YangZhiPeng
 * @date: 2023/12/29 14:09
 */
@Component
public class StrategyContext {
	private final Map<String, AbstractHandler> handlerMap = new HashMap<>(2);

	public StrategyContext(Map<String, AbstractHandler> handlerMap) {
		this.handlerMap.putAll(handlerMap);
	}

	/**
	 * 客户端调用这里，根据类型走不同的实现类（比如可以用来做支付，不同支付类型走不同实现类处理）
	 *
	 * @param type
	 * @return
	 */
	public String testType(String type) {
		return handlerMap.get(type).testType();
	}


}
