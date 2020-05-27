package com.igomall.wechat.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.igomall.entity.BaseEntity;
import com.vdurmont.emoji.EmojiParser;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "edu_wechat_auto_reply_message")
public class WeChatAutoReplyMessage extends BaseEntity<Long> {

    @NotEmpty
    @Column(nullable = false)
    private String content;

    private Boolean isEnabled;

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
}
