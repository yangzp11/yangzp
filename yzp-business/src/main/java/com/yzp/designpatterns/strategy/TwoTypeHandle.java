package com.yzp.designpatterns.strategy;

import org.springframework.stereotype.Component;

/**
 * desc
 *
 * @author:YangZhiPeng
 * @date: 2023/12/29 14:12
 */
@Component("2")
public class TwoTypeHandle implements AbstractHandler {

	@Override
	public String testType() {
		return "类型2处理";
	}
}
