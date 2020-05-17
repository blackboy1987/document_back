package com.igomall.wechat.job;

import com.igomall.wechat.service.SubscribeSummaryLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class InitJob {

    @Autowired
    private SubscribeSummaryLogService subscribeSummaryLogService;

    @Scheduled(cron = "1 0 0 * * ?")
    private void init(){
        subscribeSummaryLogService.init(new Date());
    }

}
