package com.yzp.mybatis.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author YangZhiPeng
 * @since 2022-10-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TestMsg implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "msg_id")
    private String msgId;

    private String msgContent;

    private LocalDateTime createdTime;

    private LocalDateTime updatedTime;


}
