package com.yzp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * desc
 *
 * @author YangZhiPeng
 * @date 2022/9/22 13:08
 */
@RestController
@RequestMapping("/api")
public class TestController {

    @GetMapping("/test")
    public String test(){
        return "test success";
    }
}
