package com.igomall.wechat.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.controller.admin.BaseController;
import com.igomall.entity.BaseEntity;
import com.igomall.wechat.entity.WeChatMessage;
import com.igomall.wechat.service.WechatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController("adminWeChatMessageController")
@RequestMapping("/api/admin/we_chat_message")
public class WeChatMessageController extends BaseController {

    @Autowired
    private WechatMessageService wechatMessageService;

    @PostMapping("/list")
    @JsonView(BaseEntity.ListView.class)
    public Page<WeChatMessage> list(Pageable pageable, String content,String toUserName,String fromUserName,String msgType, Date beginDate, Date endDate){
        Page<WeChatMessage> page = wechatMessageService.findPage(pageable,content,toUserName,fromUserName,msgType,beginDate,endDate);
        return page;
    }
}
