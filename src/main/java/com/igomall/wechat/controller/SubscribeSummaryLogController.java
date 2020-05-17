package com.igomall.wechat.controller;

import com.igomall.common.Order;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.controller.admin.BaseController;
import com.igomall.wechat.entity.SubscribeSummaryLog;
import com.igomall.wechat.service.SubscribeSummaryLogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("wechatSubscribeSummaryLogController")
@RequestMapping("/api/admin/subscribe_summary_log")
public class SubscribeSummaryLogController extends BaseController {

    @Autowired
    private SubscribeSummaryLogService subscribeSummaryLogService;

    @PostMapping("/list")
    public Page<SubscribeSummaryLog> list(Pageable pageable){
        pageable.setPageSize(10);
        if(StringUtils.isEmpty(pageable.getOrderProperty()) || pageable.getOrderDirection()==null){
            pageable.setOrderProperty("date");
            pageable.setOrderDirection(Order.Direction.desc);
        }
        return subscribeSummaryLogService.findPage(pageable);
    }

}
