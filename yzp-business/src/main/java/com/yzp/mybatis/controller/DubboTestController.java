package com.yzp.mybatis.controller;

import com.yzp.api.RemoteMemberService;
import com.yzp.entity.member.YzpMember;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * desc
 *
 * @author:YangZhiPeng
 * @date: 2024/9/12 10:04
 */
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class DubboTestController {

	@DubboReference
	private RemoteMemberService remoteMemberService;

	@GetMapping("/query_member")
	public YzpMember getMemberInfo(@RequestParam("memberId") Integer memberId) {
		return remoteMemberService.getMemberInfo(memberId);
	}

}
