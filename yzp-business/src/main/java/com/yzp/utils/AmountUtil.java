package com.yzp.utils;

import cn.hutool.json.JSONUtil;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * desc
 *
 * @author YangZhiPeng
 * @date 2022/8/26 18:08
 */
@Slf4j
@UtilityClass
public class AmountUtil {

    public static void main(String[] args) {
        List<BigDecimal> aa = new ArrayList<>();
        aa.add(new BigDecimal("16"));
        aa.add(new BigDecimal("45"));
        aa.add(new BigDecimal("16"));
        aa.add(new BigDecimal("16"));
        aa.add(new BigDecimal("28"));
        aa.add(new BigDecimal("33"));
        List<BigDecimal> bb = preferentialDistributionAlgorithm(aa, new BigDecimal("25"));
        log.info(JSONUtil.toJsonStr(bb));

        List<String> clothesNumList = new ArrayList<>();
        clothesNumList.add("123451");
        clothesNumList.add("123452");
        clothesNumList.add("123453");
        clothesNumList.add("123454");
        clothesNumList.add("123455");
        clothesNumList.add("123456");
        Map<String, BigDecimal>result = allocationAmountReturnMap(aa, new BigDecimal("25"), clothesNumList);
        log.info(JSONUtil.toJsonStr(result));
    }

    /**
     * 优惠金额分配算法
     *
     * @param list        明细金额集合
     * @param couponMoney 优惠金额
     * @return
     */
    public List<BigDecimal> preferentialDistributionAlgorithm(List<BigDecimal> list, BigDecimal couponMoney) {
        List<BigDecimal> tempList = new ArrayList<>();
        // 明细总和
        BigDecimal sum = BigDecimal.ZERO;
        for (BigDecimal decimal : list) {
            sum = sum.add(decimal);
        }

        for (BigDecimal oneMoney : list) {
            // 单个明细分配的优惠金额
            BigDecimal lastMoney;
            if (couponMoney.compareTo(BigDecimal.ZERO) == 0) {
                continue;
            }
            //优惠券金额大于等于合计金额
            if (couponMoney.compareTo(sum) > -1) {
                lastMoney = oneMoney;
            }
            //舍0进1
            //单个明细金额占比，除数保留位数越多，分配越精准
            BigDecimal oneMoneyScope = oneMoney.divide(sum, 5, RoundingMode.UP);
            //分配优惠券金额按比例 优惠金额末位舍0进1
            lastMoney = oneMoneyScope.multiply(couponMoney).setScale(2, RoundingMode.UP);
            //如果分配比例大于金额 则等于金额
            if (lastMoney.compareTo(oneMoney) > 0) {
                lastMoney = oneMoney;
            }
            //优惠金额去除本次优惠金额
            couponMoney = couponMoney.subtract(lastMoney);
            //总金额减去本次商品金额
            sum = sum.subtract(oneMoney);
            tempList.add(oneMoney.subtract(lastMoney));
        }
        //填平没有优惠的金额
        int tempListSize = tempList.size();
        if (tempListSize != list.size()) {
            int size = list.size() - tempListSize;
            for (int i = 0; i < size; i++) {
                tempList.add(list.get(i + tempListSize));
            }
        }
        return tempList;
    }

    /**
     * 优惠金额分配算法
     *
     * @param list        明细金额集合
     * @param couponMoney 优惠金额
     * @param clothesNumList 衣物号集合
     * @return
     */
    public Map<String, BigDecimal> allocationAmountReturnMap(List<BigDecimal> list, BigDecimal couponMoney,
                                                             List<String> clothesNumList) {
        List<BigDecimal> tempList = new ArrayList<>();
        // 明细总和
        BigDecimal sum = BigDecimal.ZERO;
        for (BigDecimal decimal : list) {
            sum = sum.add(decimal);
        }

        for (BigDecimal oneMoney : list) {
            // 单个明细分配的优惠金额
            BigDecimal lastMoney;
            if (couponMoney.compareTo(BigDecimal.ZERO) == 0) {
                continue;
            }
            //优惠券金额大于等于合计金额
            if (couponMoney.compareTo(sum) > -1) {
                lastMoney = oneMoney;
            }
            //舍0进1
            //单个明细金额占比，除数保留位数越多，分配越精准
            BigDecimal oneMoneyScope = oneMoney.divide(sum, 5, RoundingMode.UP);
            //分配优惠券金额按比例 优惠金额末位舍0进1
            lastMoney = oneMoneyScope.multiply(couponMoney).setScale(2, RoundingMode.UP);
            //如果分配比例大于金额 则等于金额
            if (lastMoney.compareTo(oneMoney) > 0) {
                lastMoney = oneMoney;
            }
            //优惠金额去除本次优惠金额
            couponMoney = couponMoney.subtract(lastMoney);
            //总金额减去本次商品金额
            sum = sum.subtract(oneMoney);
            tempList.add(oneMoney.subtract(lastMoney));
        }
        //填平没有优惠的金额
        int tempListSize = tempList.size();
        if (tempListSize != list.size()) {
            int size = list.size() - tempListSize;
            for (int i = 0; i < size; i++) {
                tempList.add(list.get(i + tempListSize));
            }
        }
        //给衣物号绑定分配后的价格
        Map<String, BigDecimal> map = new LinkedHashMap<>();
        for (int i = 0; i < clothesNumList.size(); i++) {
            map.put(clothesNumList.get(i), tempList.get(i));
        }
        return map;
    }

}
