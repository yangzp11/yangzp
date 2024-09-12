package com.yzp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzp.entity.member.YzpMember;
import com.yzp.mapper.YzpMemberMapper;
import com.yzp.service.IYzpMemberService;
import org.springframework.stereotype.Service;

/**
 * desc
 *
 * @author:YangZhiPeng
 * @date: 2024/9/12 11:44
 */
@Service
public class YzpMemberImpl extends ServiceImpl<YzpMemberMapper, YzpMember> implements IYzpMemberService {
}
