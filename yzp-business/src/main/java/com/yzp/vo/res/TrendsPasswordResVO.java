package com.yzp.vo.res;

import lombok.Data;

/**
 * 动态密码响应VO
 *
 * @author YangZhiPeng
 * @date 2023/4/27 11:18
 */
@Data
public class TrendsPasswordResVO {

    /**
     * 密码
     */
    private String password;

    /**
     * 倒计时（秒）
     */
    private Long countdown;

}
