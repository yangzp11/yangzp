package com.yzp;

import com.gobrs.async.GobrsAsync;
import com.gobrs.async.domain.AsyncResult;
import com.gobrs.async.engine.RuleThermalLoad;
import com.gobrs.async.rule.Rule;
import com.yzp.thread.async.AService;
import com.yzp.thread.async.BService;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * desc
 *
 * @author YangZhiPeng
 * @date 2022/10/8 15:16
 */
@SpringBootTest
public class GobrsAsyncTest {
    Logger logger = LoggerFactory.getLogger(GobrsAsyncTest.class);

    // 规则热加载器
    @Resource
    private RuleThermalLoad ruleThermalLoad;
    @Autowired
    private GobrsAsync gobrsAsync;
    // 热更新规则任务 无需启动程序， 只需要将规则交给 规则热加载器 即可完成接入
    public void updateRule(Rule rule) {
        // 单任务修改
        Rule r = new Rule();
        r.setName("ruleName");
        r.setContent("AService->CService->EService->GService; BService->DService->FService->HService;");
        ruleThermalLoad.load(rule);

        // 批量修改
        List<Rule> updateRules = new ArrayList<Rule>();
        updateRules.add(r);
        // updateRules.add(...);
        ruleThermalLoad.load(updateRules);
    }

    @Test
    public void gobrsAsyncGo() {
        Map<Object, Object> params = new HashMap(2);
        params.put(AService.class, "A");
        params.put(BService.class, "B");
        // 任务流程名称 , 任务流程传入参数, 任务流程超时时间
        AsyncResult asyncResult = gobrsAsync.go("ruleName", () -> params, 10000);
        logger.info("aa:{}", asyncResult);
    }

}
