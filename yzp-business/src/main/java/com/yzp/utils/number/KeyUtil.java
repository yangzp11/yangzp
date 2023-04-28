package com.yzp.utils.number;

import java.awt.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

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

    public static void main(String[] args) {
//        String jsonStr="{\n" +
//                "  \"bandWidth\": {\n" +
//                "    \"ActualRecvBwApplication\": 0,\n" +
//                "    \"ActualRecvBwAudio\": 0,\n" +
//                "    \"ActualRecvBwMax:\": 0,\n" +
//                "    \"ActualRecvBwVideo:\": 0,\n" +
//                "    \"ActualSendBwApplication:\": 0,\n" +
//                "    \"ActualSendBwAudio\": 0,\n" +
//                "    \"ActualSendBwMax\": 4294967,\n" +
//                "    \"ActualSendBwVideo:\": 0,\n" +
//                "    \"AvailRecvBwMax\": 4294967,\n" +
//                "    \"AvailSendBwMax:\": 4294967\n" +
//                "  },\n" +
//                "  \"localDateTime\": \"\",\n" +
//                "  \"shaperInfo\": {\n" +
//                "  },\n" +
//                "  \"vedioInfo\": {\n" +
//                "    \"decodedFrameRate\": 0,\n" +
//                "    \"displayedFrameRate\": 0,\n" +
//                "    \"height\": 0,\n" +
//                "    \"receivedFrameRate\": 0,\n" +
//                "    \"width\": 0\n" +
//                "  }\n" +
//                "}";
//        Map<String, Object> map = new HashMap<>();
//        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
//        for (Map.Entry<String, Object> stringObjectEntry : jsonObject.entrySet()) {
//            map.put(stringObjectEntry.getKey(), stringObjectEntry.getValue());
//            if (ObjectUtil.isEmpty(stringObjectEntry.getValue())){
//                map.put(stringObjectEntry.getKey(), 0);
//                if (stringObjectEntry.getKey().equals("localDateTime")){
//                    map.put(stringObjectEntry.getKey(), LocalDate.now());
//                }
//            }
//        }
//        map.forEach((key, value) ->{
//            System.out.println(key + ":" + value);
//        });
        LocalDateTime now = LocalDateTime.now();
        Long nowLong = now.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        System.out.println(nowLong);
        LocalDateTime now2 = LocalDateTime.ofInstant(Instant.ofEpochMilli(nowLong),  ZoneId.systemDefault());
        System.out.println(now2);
    }
}
