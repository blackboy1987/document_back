package com.igomall.wechat.entity;

import com.igomall.entity.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 关注 取消关注记录
 */
@Entity
@Table(name = "edu_wechat_subscribe_summary_log")
public class SubscribeSummaryLog extends BaseEntity<Long> {

    @NotNull
    @Column(nullable = false,updatable = false,unique = true)
    private String date;

    @NotNull
    @Min(0)
    @Column(nullable = false)
    private Long subscribeCount;

    @NotNull
    @Min(0)
    @Column(nullable = false)
    private Long unSubscribeCount;

    @NotNull
    @Column(nullable = false)
    private Long netCount;

    @NotNull
    @Min(0)
    @Column(nullable = false)
    private Long totalCount;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getSubscribeCount() {
        return subscribeCount;
    }

    public void setSubscribeCount(Long subscribeCount) {
        this.subscribeCount = subscribeCount;
    }

    public Long getUnSubscribeCount() {
        return unSubscribeCount;
    }

    public void setUnSubscribeCount(Long unSubscribeCount) {
        this.unSubscribeCount = unSubscribeCount;
    }

    public Long getNetCount() {
        return netCount;
    }

    public void setNetCount(Long netCount) {
        this.netCount = netCount;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }
}
