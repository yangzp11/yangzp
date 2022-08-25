package com.yzp.utils.date;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.util.ObjectUtil;
import lombok.Data;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * LocalDateTime 工具类
 *
 * @author YangZhiPeng
 * @date 2021-10-27 14:10
 */
//工具类注解
@UtilityClass
@Slf4j
public class LocalDateTimeUtils {

    /**
     * 比较两个日期相差多少天
     *
     * @param time1 time1
     * @param time2 time2
     * @return 相差的天数
     */
    public long compareDays(LocalDateTime time1, LocalDateTime time2) {
        if (ObjectUtil.isEmpty(time1) || ObjectUtil.isEmpty(time2)) {
            log.error("比较两个日期相差多少天出现空日期");
            return 0;
        }
        return Duration.between(time1, time2).toDays();
    }

    /**
     * 比较两个日期相差多少小时
     *
     * @param time1 time1
     * @param time2 time2
     * @return 相差的天数
     */
    public long compareHours(LocalDateTime time1, LocalDateTime time2) {
        if (ObjectUtil.isEmpty(time1) || ObjectUtil.isEmpty(time2)) {
            log.error("比较两个日期相差多少小时出现空日期");
            return 0;
        }
        return Duration.between(time1, time2).toHours();
    }

    /**
     * 比较两个日期相差多少分钟
     *
     * @param time1 time1
     * @param time2 time2
     * @return 相差的天数
     */
    public long compareMinutes(LocalDateTime time1, LocalDateTime time2) {
        if (ObjectUtil.isEmpty(time1) || ObjectUtil.isEmpty(time2)) {
            log.error("比较两个日期相差多少分钟出现空日期");
            return 0;
        }
        return Duration.between(time1, time2).toMinutes();
    }

    /**
     * 比较两个日期相差多少小时,多余的直接进+1小时
     *
     * @param time1 time1
     * @param time2 time2
     * @return 相差的小时数
     */
    public long compareHoursEnter(LocalDateTime time1, LocalDateTime time2) {
        if (ObjectUtil.isEmpty(time1) || ObjectUtil.isEmpty(time2)) {
            log.error("比较两个日期相差多少小时出现空日期");
            return 0;
        }
        long hours = Duration.between(time1, time2).toHours();
        //是否多余了分钟，秒忽略不算
        if (Duration.between(time1, time2).toMinutes() - (hours * 60) > 0) {
            hours = hours + 1;
        }
        return hours;
    }

    /**
     * 两个时间相差距离多少天多少小时多少分多少秒
     *
     * @param startTime 时间参数 1 格式：1990-01-01 12:00:00
     * @param endTime   时间参数 2 格式：2009-01-01 12:00:00
     * @return String 返回值为：xx天xx时xx分xx秒
     */
    public static String getDistanceTime(String startTime, String endTime) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date one;
        Date two;
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        try {
            one = df.parse(startTime);
            two = df.parse(endTime);
            long time1 = one.getTime();
            long time2 = two.getTime();
            long diff;
            if (time1 < time2) {
                diff = time2 - time1;
            } else {
                diff = time1 - time2;
            }
            day = diff / (24 * 60 * 60 * 1000);
            hour = (diff / (60 * 60 * 1000) - day * 24);
            min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
            sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        } catch (ParseException e) {
            log.error("比较两个时间相差距离异常:{}", e.getMessage());
        }
        return day + "天" + hour + "时" + min + "分" + sec + "秒";
    }

    /**
     * 获取两个时间点的月份差
     *
     * @param dt1 第一个时间点
     * @param dt2 第二个时间点
     * @return int，即需求的月数差
     */
    public int monthDiff(LocalDateTime dt1, LocalDateTime dt2) {
        //获取第一个时间点的月份
        int month1 = dt1.getMonthValue();
        //获取第一个时间点的年份
        int year1 = dt1.getYear();
        //获取第一个时间点的月份
        int month2 = dt2.getMonthValue();
        //获取第一个时间点的年份
        int year2 = dt2.getYear();
        //返回两个时间点的月数差
        return (year2 - year1) * 12 + (month2 - month1);
    }

    /**
     * 比较 time1是否BETWEEN time2 AND time3
     *
     * @param time1 time1
     * @param time2 time2
     * @param time3 time3
     * @return Boolean
     */
    public Boolean compareBetween(LocalDateTime time1, LocalDateTime time2, LocalDateTime time3) {
        if (ObjectUtil.isEmpty(time1) || ObjectUtil.isEmpty(time2) || ObjectUtil.isEmpty(time3)) {
            log.error("比较 time1是否BETWEEN time2 AND time3出现空日期");
            return Boolean.FALSE;
        }

        if (time1.isAfter(time2) && time1.isBefore(time3)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * 判断两个时间段是否重叠
     *
     * @param slot1 slot1
     * @param slot2 slot2
     * @return boolean
     */
    public static boolean overlapped(TimeSlot slot1, TimeSlot slot2) {
        TimeSlot previous, next;
        previous = slot1.startTime.isBefore(slot2.startTime) ? slot1 : slot2;
        next = slot2.startTime.isAfter(slot1.startTime) ? slot2 : slot1;
        // 这里业务需要，允许时间点的重叠
        // 例如某个时间段的起始时间：2020-06-29 00:00:00
        // 和另一个时间段的终止时间：2020-06-29 00:00:00
        // 它们俩可以有交点。如果不需要这种逻辑只把le改成lt
        // ，ge改成gt就可
        return !(le(previous, next) || ge(previous, next));
    }

    /**
     * 构造一个时间段
     *
     * @param startTime startTime
     * @param endTime   endTime
     * @return TimeSlot
     */
    public static TimeSlot buildSlot(LocalDateTime startTime, LocalDateTime endTime) {
        return new TimeSlot(startTime, endTime);
    }

    /**
     * less equal
     * 小于等于
     *
     * @param prev prev
     * @param next next
     * @return boolean
     */
    private static boolean le(TimeSlot prev, TimeSlot next) {
        return lt(prev, next) || next.endTime.isEqual(prev.startTime);
    }

    /**
     * greater equal
     * 大于等于
     *
     * @param prev prev
     * @param next next
     * @return boolean
     */
    private static boolean ge(TimeSlot prev, TimeSlot next) {
        return gt(prev, next) || prev.endTime.isEqual(next.startTime);
    }

    /**
     * greater than
     * 大于
     *
     * @param prev prev
     * @param next next
     * @return boolean
     */
    private static boolean gt(TimeSlot prev, TimeSlot next) {
        return prev.endTime.isBefore(next.startTime);
    }

    /**
     * less than
     * 小于
     *
     * @param prev prev
     * @param next next
     * @return boolean
     */
    private static boolean lt(TimeSlot prev, TimeSlot next) {
        return next.endTime.isBefore(prev.startTime);
    }

    /**
     * 时间段类
     */
    @Data
    static class TimeSlot {
        private LocalDateTime startTime;
        private LocalDateTime endTime;

        public TimeSlot(LocalDateTime startTime, LocalDateTime endTime) {
            if (startTime.isAfter(endTime)) {
                this.startTime = endTime;
                this.endTime = startTime;
            } else {
                this.startTime = startTime;
                this.endTime = endTime;
            }
        }
    }

    /**
     * 获取今天的时间范围
     *
     * @param day 0今天，-1昨天 1明天
     * @return 返回长度为2的字符串集合，如：[2022-11-11 00:00:00, 2022-11-11 24:00:00]
     */
    public static List<LocalDateTime> getToday(Integer day) {
        List<LocalDateTime> dataList = new ArrayList<>(2);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.DATE, day);
        String today = dateFormat.format(calendar.getTime());
        dataList.add(LocalDateTime.parse(today + " 00:00:00", DatePattern.NORM_DATETIME_FORMATTER));
        dataList.add(LocalDateTime.parse(today + " 24:00:00", DatePattern.NORM_DATETIME_FORMATTER));
        return dataList;
    }

    /**
     * 获取本周的时间范围
     *
     * @return 返回长度为2的字符串集合，如：[2022-05-02 00:00:00, 2022-05-08 24:00:00]
     */
    public static List<LocalDateTime> getCurrentWeek() {

        List<LocalDateTime> dataList = new ArrayList<>(2);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);//设置周一为一周之内的第一天
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        LocalDateTime monday = LocalDateTime.parse(dateFormat.format(calendar.getTime()) + " 00:00:00", DatePattern.NORM_DATETIME_FORMATTER);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        LocalDateTime sunday = LocalDateTime.parse(dateFormat.format(calendar.getTime()) + " 24:00:00", DatePattern.NORM_DATETIME_FORMATTER);
        dataList.add(monday);
        dataList.add(sunday);
        return dataList;
    }

    /**
     * 获取本月的时间范围
     *
     * @param month month
     * @return 返回长度为2的字符串集合，如：[2022-11-01 00:00:00, 2022-11-30 24:00:00]
     */
    public static List<LocalDateTime> getCurrentMonth(Integer month) {

        List<LocalDateTime> dataList = new ArrayList<>(2);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        LocalDateTime firstDayOfMonth = LocalDateTime.parse(dateFormat.format(calendar.getTime()) + " 00:00:00", DatePattern.NORM_DATETIME_FORMATTER);
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        LocalDateTime lastDayOfMonth = LocalDateTime.parse(dateFormat.format(calendar.getTime()) + " 24:00:00", DatePattern.NORM_DATETIME_FORMATTER);
        dataList.add(firstDayOfMonth);
        dataList.add(lastDayOfMonth);
        return dataList;
    }

    /**
     * 获取本年的时间范围
     *
     * @return 返回长度为2的字符串集合，如：[2022-01-01 00:00:00, 2022-12-31 24:00:00]
     */
    public static List<String> getCurrentYear() {

        List<String> dataList = new ArrayList<>(2);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.YEAR, 0);
        calendar.set(Calendar.DAY_OF_YEAR, 1);
        String firstDayOfYear = dateFormat.format(calendar.getTime()) + " 00:00:00";
        calendar.add(Calendar.YEAR, 1);
        calendar.set(Calendar.DAY_OF_YEAR, 0);
        String lastDayOfYear = dateFormat.format(calendar.getTime()) + " 24:00:00";
        dataList.add(firstDayOfYear);
        dataList.add(lastDayOfYear);
        return dataList;
    }

    /**
     * 周一 ===》周日集合排序
     *
     * @param arr list
     * @return sort
     */
    public List<String> handleWeekSort(List<String> arr) {
        //冒泡排序
        for (int i = 0; i < arr.size() - 1; i++) {
            boolean flag = true;
            for (int j = 0; j < arr.size() - i - 1; j++) {
                //巧妙的值转换
                if (returnWeek(arr.get(j)) > returnWeek(arr.get(j + 1))) {
                    String temp = arr.get(j);
                    arr.set(j, arr.get(j + 1));
                    arr.set(j + 1, temp);
                    // 改变flag，提前结束
                    flag = false;
                }
            }
            if (flag) {
                break;
            }
        }
        return arr;
    }

    /**
     * 返回周几对应数字
     *
     * @param var 值
     * @return int
     */
    public int returnWeek(String var) {
        switch (var) {
            case "周一":
                return 1;
            case "周二":
                return 2;
            case "周三":
                return 3;
            case "周四":
                return 4;
            case "周五":
                return 5;
            case "周六":
                return 6;
            case "周日":
                return 7;
        }
        return 0;
    }

}
