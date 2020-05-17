
package com.igomall.wechat.service;

import com.igomall.wechat.entity.SubscribeSummaryLog;
import com.igomall.service.BaseService;

import java.util.Date;

/**
 * Service - 地区
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
public interface SubscribeSummaryLogService extends BaseService<SubscribeSummaryLog, Long> {

    boolean dateExists(String date);

    SubscribeSummaryLog findByDate(String date);

    void init(Date date);
}