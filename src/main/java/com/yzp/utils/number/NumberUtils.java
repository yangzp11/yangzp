package com.yzp.utils.number;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 数字工具类
 *
 * @author YangZhiPeng
 * @date 2022/8/23 17:36
 */
public class NumberUtils {

    private static final AtomicInteger atomicInteger = new AtomicInteger(0);

    /**
     * 根据时间戳+atomicInteger生成13位长度数字，只能支持小并发不重复，仅适用于后台系统
     *
     * @return Number
     */
    public static String getThirteenNumber() {
        //后10位时间戳
        String timeStr = String.valueOf(System.currentTimeMillis()).substring(3);
        // 递增生成最大999的递增ID
        if (atomicInteger.get() == 999) {
            atomicInteger.set(0);
        }
        int atomicIntegerNum = atomicInteger.getAndIncrement();
        String atomicString = String.valueOf(atomicIntegerNum);
        if (atomicString.length() < 3) {
            switch (atomicString.length()) {
                case 1:
                    return timeStr.concat("00" + atomicString);
                case 2:
                    return timeStr.concat("0" + atomicString);
            }
        }
        return timeStr.concat(atomicString);
    }

}
