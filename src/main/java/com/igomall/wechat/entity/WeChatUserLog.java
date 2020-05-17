package com.igomall.wechat.entity;

import com.igomall.entity.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "edu_wechat_user_log")
public class WeChatUserLog extends BaseEntity<Long> {

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false,updatable = false)
    private WeChatUser weChatUser;

    @NotEmpty
    @Column(nullable = false,updatable = false)
    private String event;

    @NotEmpty
    @Column(nullable = false,updatable = false)
    private String memo;

    @NotEmpty
    @Column(nullable = false,updatable = false)
    private String content;

    public WeChatUser getWeChatUser() {
        return weChatUser;
    }

    public void setWeChatUser(WeChatUser weChatUser) {
        this.weChatUser = weChatUser;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
