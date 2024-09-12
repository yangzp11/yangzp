package com.yzp.dubbo;

import com.yzp.api.RemoteMemberService;
import com.yzp.entity.OrderInfo;
import com.yzp.entity.member.YzpMember;
import com.yzp.service.IYzpMemberService;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

/**
 * desc
 *
 * @author:YangZhiPeng
 * @date: 2024/9/12 11:48
 */
@RequiredArgsConstructor
@Service
@DubboService
public class RemoteMemberServiceImpl implements RemoteMemberService {

	private final IYzpMemberService memberService;

	@Override
	public YzpMember getMemberInfo(Integer memberId) {
		return memberService.getById(memberId);
	}

}
