package com.yzp.mybatis.service.processor;


import com.yzp.entity.OrderInfo;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * desc
 *
 * @author YangZhiPeng
 * @date 2022/8/29 10:48
 */
public interface OrderProcessService {

    /**
     * 初始化mysql数据
     */
    void initMysqlData();

    /**
     * 导出数据
     *
     * @param operate
     * @param clothesNumList
     * @param response
     */
    void exportData(Integer operate, List<String> clothesNumList, HttpServletResponse response) throws Exception;

    /**
     * 测试spring事务
     *
     * @return
     */
    OrderInfo testTransactional();

}
