package com.yzp.test.javatime;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * desc 
 *
 * @author YangZhiPeng
 * @date 2023/2/28 9:37
 */
public class TimeTest {

    public static void main(String[] args) {
        Calendar calendar = GregorianCalendar.getInstance();
        System.out.println(calendar.getTimeInMillis());

        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(calendar.getTimeInMillis()), ZoneId.systemDefault());
        System.out.println(localDateTime);
        LocalDateTime localDateTime1 = LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault());
        System.out.println(localDateTime1);
        System.out.println(Instant.now().getEpochSecond());
    }

}
