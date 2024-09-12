package com.yzp.api;

import com.yzp.entity.member.YzpMember;

/**
 * desc
 *
 * @author:YangZhiPeng
 * @date: 2024/9/12 11:47
 */
public interface RemoteMemberService {

	YzpMember getMemberInfo(Integer memberId);
}
