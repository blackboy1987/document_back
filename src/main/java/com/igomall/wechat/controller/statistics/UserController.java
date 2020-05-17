package com.igomall.wechat.controller.statistics;

import com.igomall.controller.admin.BaseController;
import com.igomall.wechat.entity.SubscribeSummaryLog;
import com.igomall.wechat.service.SubscribeSummaryLogService;
import com.igomall.util.Date8Utils;
import com.igomall.wechat.util.DataCubeUtils;
import com.igomall.wechat.util.response.datacube.DataCubeUserSummaryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController("weChatStatisticsUserController")
@RequestMapping("/api/we_chat/statistics/user")
public class UserController extends BaseController {

    @Autowired
    private SubscribeSummaryLogService subscribeSummaryLogService;


    @GetMapping("/summary")
    public DataCubeUserSummaryResponse summary(){
        Date beginDate = Date8Utils.getNextDay(-5);
        Date endDate = Date8Utils.getNextDay(-3);
        DataCubeUserSummaryResponse dataCubeUserSummaryResponse = DataCubeUtils.userSummary(beginDate,endDate);

        return dataCubeUserSummaryResponse;
    }

    @GetMapping("/cumulate")
    public DataCubeUserSummaryResponse cumulate(){
        Date beginDate = Date8Utils.getNextDay(-5);
        Date endDate = Date8Utils.getNextDay(-3);
        DataCubeUserSummaryResponse dataCubeUserSummaryResponse = DataCubeUtils.userCumulate(beginDate,endDate);
        return dataCubeUserSummaryResponse;
    }












    @GetMapping("/index")
    public Map<String,Object> index(){
        Map<String,Object> data = new HashMap<>();
        Date now = new Date();
        for (int i=0;i<100;i++){
            Date date = Date8Utils.getNextDay(i*-1);
            // 关注
            Long subscribeCount = jdbcTemplate.queryForObject("select count(id) from edu_wechat_user where (status=1 or status=0) and last_modified_date like '"+Date8Utils.formatDateToString(date,"yyyy-MM-dd")+"%'",Long.class);
            // 取消关注
            Long unSubscribeCount = jdbcTemplate.queryForObject("select  count(id) from edu_wechat_user where status=2 and last_modified_date like '"+Date8Utils.formatDateToString(date,"yyyy-MM-dd")+"%'",Long.class);
            Long netCount = subscribeCount-unSubscribeCount;

            SubscribeSummaryLog subscribeSummaryLog = new SubscribeSummaryLog();
            subscribeSummaryLog.setDate(Date8Utils.formatDateToString(date,"yyyy-MM-dd"));
            subscribeSummaryLog.setNetCount(netCount);
            subscribeSummaryLog.setSubscribeCount(subscribeCount);
            subscribeSummaryLog.setUnSubscribeCount(unSubscribeCount);
            subscribeSummaryLog.setTotalCount(0L);
            subscribeSummaryLogService.save(subscribeSummaryLog);

        }

        return data;
    }

}
