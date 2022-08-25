package com.yzp.thread;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 自增号批量生成
 *
 * @author YangZhiPeng
 * @date 2022/8/25 16:28
 */
@Slf4j
@Component
public class ClothesNumGenerate {

    private final ThreadLocal<StringBuffer> stringBuilderThreadLocal = new ThreadLocal<>();

    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public List<String> getOutClothesNumberListNew(Integer num, Integer tenantId) {

        RLock lock = redissonClient.getLock("OUT_CLOTHES_NUMBER_INC_LOCK_" + tenantId);

        try {
            lock.lock(10, TimeUnit.SECONDS);
            List<String> numList = new ArrayList<>();
            String key = "OUT_CLOTHES_NUMBER:CURRENT_MAX_KEY_" + tenantId;

            //获取当前最大编码值
            Long lastOutClothesNumber = this.getLastOutClothesNumber(tenantId);

            // 外部衣物号自增
            AtomicLong outOrderClothesNumberAtomicLong = new AtomicLong(lastOutClothesNumber);
            for (int i = 0; i < num; i++) {
                long incrementAndGet = outOrderClothesNumberAtomicLong.incrementAndGet();
                String value = String.valueOf(incrementAndGet);
                //达到999999；由于先+1了所以用000000判断，用完了，重新初始化外部衣物号
                if ("000000".equals(value.substring(4))) {
                    value = this.getOutOrderClothesIfMax(tenantId, Integer.valueOf(value.substring(0, 1)));
                    outOrderClothesNumberAtomicLong = new AtomicLong(Long.parseLong(value));
                }
                numList.add(value);
            }
            //将新的最大值同步到redis缓存
            redisTemplate.opsForValue().set(key, numList.get(num - 1));
            //log.info("门店:[{}],本次生成外部衣物编号,衣物号数量:[{}],衣物号为:[{}]", tenantId, num, numList);
            return numList;
        } catch (Exception e) {
            //log.error("门店:[{}],获取外部衣物编号,获取redis分布式锁异常,衣物号数量:[{}],{}", tenantId, num, e);
            return null;
        } finally {
            //log.info("获取外部衣物编号，释放redis分布式锁");
            lock.unlock(); //释放锁
        }

    }

    public Long getLastOutClothesNumber(Integer tenantId) {

        String key = "OUT_CLOTHES_NUMBER:CURRENT_MAX_KEY_" + tenantId;
        long lastOutClothesNumber;
        //redis获取外部衣物号当前最大编码值
        Object object = redisTemplate.opsForValue().get(key);
        if (null != object) {
            return Long.valueOf(object.toString());
        }

        // TODO 数据库查询最大的衣物号
        lastOutClothesNumber = 0;
        //为0空的；初始化外部衣物号
        if (lastOutClothesNumber == 0) {
            return Long.parseLong(getOutClothesZeroNumber(tenantId));
        }
        return lastOutClothesNumber;
    }

    /**
     * 为0的外部衣物号
     */
    public String getOutClothesZeroNumber(Integer tenantId) {
        StringBuffer outClothesAccessoryNum = new StringBuffer();
        // 新增
        outClothesAccessoryNum.append("1");
        outClothesAccessoryNum.append(String.format("%03d", tenantId));
        // incrementAndGet 返回加1后的新值
        outClothesAccessoryNum.append(String.format("%04d", 0));
        stringBuilderThreadLocal.set(outClothesAccessoryNum);
        return stringBuilderThreadLocal.get().toString();
    }

    /**
     * 达到999999后 首位自增1
     */
    public String getOutOrderClothesIfMax(Integer tenantId, Integer firstValue) {
        StringBuffer outClothesAccessoryNum = new StringBuffer();
        // 第一位+1
        outClothesAccessoryNum.append(firstValue + 1);
        outClothesAccessoryNum.append(String.format("%03d", tenantId));
        // incrementAndGet 返回加1后的新值
        outClothesAccessoryNum.append(String.format("%06d", 1));
        stringBuilderThreadLocal.set(outClothesAccessoryNum);
        return stringBuilderThreadLocal.get().toString();
    }
}
