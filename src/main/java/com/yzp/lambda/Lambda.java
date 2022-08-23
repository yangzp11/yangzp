package com.yzp.lambda;

import cn.hutool.core.util.ObjectUtil;
import com.yzp.mybatis.entity.Test;
import com.yzp.utils.lambda.CollectorsUtil;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * lambda 常用方法
 *
 * @author YangZhiPeng
 * @date 2022/8/23 11:08
 */
@Slf4j
public class Lambda {

    public static void main(String[] args) {

        List<Test> testList = new ArrayList<>();

        Test test01 = new Test();
        test01.setTestId("111");
        test01.setTestType(1);
        test01.setTestMoney(new BigDecimal("20"));
        test01.setTestStatus(1);
        testList.add(test01);

        Test test02 = new Test();
        test02.setTestId("222");
        test02.setTestType(2);
        test02.setTestMoney(new BigDecimal("30"));
        test02.setTestStatus(0);
        testList.add(test02);

        Test test03 = new Test();
        test03.setTestId("222");
        test03.setTestType(2);
        test03.setTestStatus(2);
        test03.setTestMoney(new BigDecimal("30"));
        testList.add(test03);

        BigDecimal sum = testList.stream().collect(CollectorsUtil.summingBigDecimal(Test::getTestMoney));
        System.out.println("总金额" + sum);
        List<Test> repeatList = testList.stream().filter(CollectorsUtil.repeatByKey(Test::getTestId)).collect(Collectors.toList());
        repeatList.forEach(System.out::println);
        //排序Comparator.nullsLast/nullFirst(Integer::compareTo)，可防止空指针，为空排到最后
        List<Test> sortedList = testList.stream().sorted(Comparator.comparing(Test::getTestStatus, Comparator.nullsLast(Integer::compareTo)).reversed()).collect(Collectors.toList());
        sortedList.forEach(System.out::println);
        //根据不同条件分组聚合
        Map<Integer, Test> testMap = sortedList.parallelStream().collect(
                Collectors.groupingBy(x -> {
                            //类型判断，根据啥分组
                            if (x.getTestType().equals(1)) {
                                return x.getGroupIdOne();
                            } else {
                                return x.getGroupIdTwo();
                            }
                        },
                        Collectors.collectingAndThen(Collectors.toList(), item -> {
                            Test resVO = new Test();
                            Integer typeId = item.get(0).getTestType();
                            resVO.setTestType(typeId);
                            //计数
                            resVO.setCount(Math.toIntExact(item.stream().filter(key -> key.getTestStatus().equals(1)).count()));
                            //统计金额
                            BigDecimal money = item.stream().map(x -> ObjectUtil.isNotEmpty(x.getTestMoney()) ? x.getTestMoney() : BigDecimal.ZERO).reduce(BigDecimal.ZERO, BigDecimal::add);
                            resVO.setTestMoney(money);
                            return resVO;
                        })));
        if (log.isInfoEnabled()){
            log.info("testMap:{}", testMap);
        }

    }
}
