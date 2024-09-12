package com.yzp.entity.member;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

/**
 * desc
 *
 * @author:YangZhiPeng
 * @date: 2024/9/12 11:42
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class YzpMember implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@TableId(type = IdType.AUTO)
	private Integer memberId;

	private String memberNumber;

	private String memberName;

}
