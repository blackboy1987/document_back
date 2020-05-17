package com.igomall.wechat.entity;

import com.igomall.entity.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 关注 取消关注记录
 */
@Entity
@Table(name = "edu_wechat_subscribe_log")
public class SubscribeLog extends BaseEntity<Long> {

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false,updatable = false)
    private WeChatUser weChatUser;

    @NotNull
    @Column(nullable = false,updatable = false)
    private Integer status;

    @NotEmpty
    @Column(nullable = false,updatable = false)
    private String memo;


    public WeChatUser getWeChatUser() {
        return weChatUser;
    }

    public void setWeChatUser(WeChatUser weChatUser) {
        this.weChatUser = weChatUser;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
