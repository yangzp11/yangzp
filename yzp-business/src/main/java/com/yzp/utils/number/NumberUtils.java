package com.yzp.utils.number;

import java.awt.*;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
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

    /**
     * 十进制数字转二进制
     */
    public static String tenToTwo1(Integer num) {
        StringBuilder result = new StringBuilder();
        for (int i = num; i > 0; i /= 2) {
            result.insert(0, i % 2);
        }
        return result.toString();
    }

    public static String tenToTwo2(Integer num) {
        return Integer.toBinaryString(num);
    }

    public static Integer twoToTen(String num) throws AWTException {
        return new BigInteger(num, 2).intValue();
    }
/*    public static void main(String[] args) {
        System.out.println(11111);
        try {
            //实例化机器人
            Robot robot = new Robot();
            //先移动到开始菜单的位置
            robot.mouseMove(24, 880);
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            robot.delay(50);

            //移动到运行菜单的位置，并且点击
            robot.mouseMove(245, 666);
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            robot.delay(50);

            //robot按下CMD键
            robot.keyPress(KeyEvent.VK_C);
            robot.keyPress(KeyEvent.VK_M);
            robot.keyPress(KeyEvent.VK_D);
            robot.delay(50);

            //点击确定，进入CMD控制台
            robot.mouseMove(145, 745);
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            robot.delay(50);
            int[] keyIpconfig = {KeyEvent.VK_I,KeyEvent.VK_P,KeyEvent.VK_C,KeyEvent.VK_O,KeyEvent.VK_N,KeyEvent.VK_F,KeyEvent.VK_I,KeyEvent.VK_G,KeyEvent.VK_ENTER};
            KeyUtil.pressKeys(robot, keyIpconfig, 50);
            robot.keyPress(KeyEvent.VK_ENTER);
            //关机 shutdown -s -t 0
//            //输入dir命令
//            int[] keyDir = {KeyEvent.VK_D,KeyEvent.VK_I,KeyEvent.VK_R,KeyEvent.VK_ENTER};
//            KeyUtil.pressKeys(robot, keyDir, 500);
//
//            //输入ipconfig命令
//            int[] keyIpconfig = {KeyEvent.VK_I,KeyEvent.VK_P,KeyEvent.VK_C,KeyEvent.VK_O,KeyEvent.VK_N,KeyEvent.VK_F,KeyEvent.VK_I,KeyEvent.VK_G,KeyEvent.VK_ENTER};
//            KeyUtil.pressKeys(robot, keyIpconfig, 500);
//            robot.keyPress(KeyEvent.VK_ENTER);
//            //输入exit命令
//            int[] keyExit = {KeyEvent.VK_E,KeyEvent.VK_X,KeyEvent.VK_I,KeyEvent.VK_T,KeyEvent.VK_ENTER};
//            KeyUtil.pressKeys(robot, keyExit, 500);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    public static void main(String[] args) {
        MathContext mathContext = new MathContext(3, RoundingMode.HALF_UP);
    }

}
