package com.yzp.utils.client;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.io.Serializable;

/**
 * 引入一个简简单单的 PairUtils 用于返回值返回两个元素
 *
 * @author YangZhiPeng
 */
@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
public final class PairUtils<L, R> implements Serializable {

    private static final long serialVersionUID = 4201666753585653168L;

    /**
     * 左边值
     */
    @NonNull
    private final L left;
    /**
     * 右边值
     */
    @NonNull
    private final R right;

    private PairUtils(@NonNull L left, @NonNull R right) {
        this.left = left;
        this.right = right;
    }

    /**
     * 根据等号左边的泛型 自动构造合适的 PairUtils
     */
    public static <L, R> PairUtils<L, R> of(@NonNull L left, @NonNull R right) {
        return new PairUtils<>(left, right);
    }

}
