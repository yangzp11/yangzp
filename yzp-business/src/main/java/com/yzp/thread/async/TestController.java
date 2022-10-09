package com.yzp.thread.async;

import cn.hutool.json.JSONUtil;
import com.gobrs.async.GobrsAsync;
import com.gobrs.async.domain.AsyncResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * desc
 *
 * @author YangZhiPeng
 * @date 2022/10/9 11:05
 */
@RestController
@RequestMapping("/test_async")
@Slf4j
@RequiredArgsConstructor
public class TestController {

    private final GobrsAsync gobrsAsync;

    @GetMapping("/test1")
    public AsyncResult gobrsAsyncGo() {
        Map<Object, Object> params = new HashMap(3);
//        params.put(AService.class, "A");
//        params.put(BService.class, "B");
//        params.put(CService.class, "C");
        // 任务流程名称 , 任务流程传入参数, 任务流程超时时间
        AsyncResult asyncResult = gobrsAsync.go("ruleName", () -> params, 10000);
        log.info("aa:{}", JSONUtil.toJsonStr(asyncResult));
        return asyncResult;
    }
}
