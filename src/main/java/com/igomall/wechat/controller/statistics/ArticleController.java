package com.igomall.wechat.controller.statistics;


import com.igomall.util.Date8Utils;
import com.igomall.wechat.util.DataCubeUtils;
import com.igomall.wechat.util.response.datacube.DataCubeArticleSummaryResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController("weChatArticleControllerController")
@RequestMapping("/api/we_chat/statistics/article")
public class ArticleController {

    @GetMapping("/summary")
    public DataCubeArticleSummaryResponse summary(){
        Date date = Date8Utils.getNextDay(-1);
        return DataCubeUtils.getArticleSummary(date);
    }


}
