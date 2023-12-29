package com.yzp.designpatterns.strategy;

import org.springframework.stereotype.Component;

/**
 * desc
 *
 * @author:YangZhiPeng
 * @date: 2023/12/29 14:11
 */
@Component("1")
public class OneTypeHandle implements AbstractHandler {

	@Override
	public String testType() {
		return "类型1处理";
	}

}
