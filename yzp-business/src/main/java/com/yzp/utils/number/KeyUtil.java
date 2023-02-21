package com.yzp.utils.number;

import java.awt.*;

/**
 * desc
 *
 * @author YangZhiPeng
 * @date 2023/2/17 16:39
 */
public class KeyUtil {

    /**
     * 键盘输入方法
     *
     * @param robot  机器人类
     * @param keys  输入键盘键名
     * @param delay  延迟时间
     * @return void 无
     *
     */
    public static void pressKeys(Robot robot, int[] keys, int delay){

        //循环遍历数组并赋值
        for (int key : keys) {
            robot.keyPress(key);
            robot.keyRelease(key);
            robot.delay(delay);
        }
    }

}
