package com.yzp.controller;

import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;

/**
 * desc
 *
 * @author YangZhiPeng
 * @date 2022/9/27 17:53
 */
@RequiredArgsConstructor
public class WeChatController {

    private final WxMpService wxMpService;

    public String push(){
        WxMpTemplateMessage message = new WxMpTemplateMessage();
        //TODO
        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(message);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return "aa";
    }
}
