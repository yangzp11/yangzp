package com.yzp.utils.map;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.yzp.utils.client.OkHttpUtil;
import com.yzp.utils.client.WebClientHttpUtils;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * QQMapUtil
 *
 * @author YangZhiPeng
 * @date 2022/8/31 10:57
 */
@Slf4j
@UtilityClass
public class QQMapUtil {

    /**
     * 根据地址转坐标
     *
     * @param address 地址详情
     * @return 纬经度
     */
    public String getCoordinate(String address) {

        // KEY
        //String key = "BUMBZ-OHAWU-WHJV4-BLBF3-GM57V-XQFFS";
        // KEY
        String key = "4A6BZ-HN6CP-MKHDB-VO64C-VTCCV-6ZBHM";
        // Secret key（ SK ）
        //String sk = "f63HaZZ3wcNXJNpjm9L31cV0UOtEHGlN";
        // Secret key（ SK ）
        String sk = "2zjzhiDbKY5UqMDkBIsKcXb5rn3iPy8e";
        // 签名计算
        String sig = SecureUtil.md5("/ws/geocoder/v1/?address=" + address + "&key=" + key + sk);
//        Map<String, String> paramsMap = new HashMap<>(3);
//        paramsMap.put("address", address);
//        paramsMap.put("key", key);
//        paramsMap.put("sig", sig);
        // 发送请求
        String response = OkHttpUtil.builder().url("https://apis.map.qq.com/ws/geocoder/v1/")
                .addParam("address", address)
                .addParam("key", key)
                .addParam("sig", sig)
                .get().async();
        log.info("根据地址转坐标,获取结果:{}", response);
        JSONObject jsonObject = JSONUtil.parseObj(response);
        Integer status = jsonObject.getInt("status");
        if (status.equals(0)) {
            JSONObject result = jsonObject.getJSONObject("result");
            JSONObject location = result.getJSONObject("location");
            String lng = location.getStr("lng");
            String lat = location.getStr("lat");
            return lat + "," + lng;
        }
        log.error("根据地址转坐标异常:{}", response);
        return "";
    }

}
