package com.yzp.mybatis.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;
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
public class TestUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer userId;

    private BigDecimal balance;

    private LocalDateTime createdTime;

    private LocalDateTime updatedTime;


}
