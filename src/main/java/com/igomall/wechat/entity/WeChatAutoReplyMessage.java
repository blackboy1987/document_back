package com.igomall.wechat.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.igomall.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "edu_wechat_auto_reply_message")
public class WeChatAutoReplyMessage extends BaseEntity<Long> {

    @NotEmpty
    @Column(nullable = false,unique = true)
    private String msgKey;

    @NotEmpty
    @Column(nullable = false)
    private String content;

    private Boolean isEnabled;

    private String callback;

    public String getMsgKey() {
        return msgKey;
    }

    public void setMsgKey(String msgKey) {
        this.msgKey = msgKey;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }
}
