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

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "edu_wechat_message")
public class WeChatMessage extends BaseEntity<Long> {

    @JsonView({ListView.class})
    private String content;

    @JsonView({ListView.class})
    private Long createTime;

    @JsonView({ListView.class})
    private String toUserName;

    @JsonView({ListView.class})
    private String fromUserName;

    @JsonView({ListView.class})
    private String msgType;

    @JsonView({ListView.class})
    private Long msgId;

    @JsonView({ListView.class})
    private String mediaId;

    @JsonView({ListView.class})
    private String picUrl;

    @JsonView({ListView.class})
    private String thumbMediaId;

    @JsonView({ListView.class})
    private Double locationX;

    @JsonView({ListView.class})
    private Double locationY;

    @JsonView({ListView.class})
    @JacksonXmlCData
    private String label;

    @JsonView({ListView.class})
    private Integer scale;

    @Column(length = 4000)
    private String receiveContent;

    public String getContent() {
        if(StringUtils.isNotEmpty(content)){
            return EmojiParser.parseToUnicode(content);
        }
        return content;
    }

    public void setContent(String content) {
        if(StringUtils.isNotEmpty(content)){
            this.content = EmojiParser.parseToAliases(content);
        }else{
            this.content = content;
        }
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public Long getMsgId() {
        return msgId;
    }

    public void setMsgId(Long msgId) {
        this.msgId = msgId;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getThumbMediaId() {
        return thumbMediaId;
    }

    public void setThumbMediaId(String thumbMediaId) {
        this.thumbMediaId = thumbMediaId;
    }

    public Double getLocationX() {
        return locationX;
    }

    public void setLocationX(Double locationX) {
        this.locationX = locationX;
    }

    public Double getLocationY() {
        return locationY;
    }

    public void setLocationY(Double locationY) {
        this.locationY = locationY;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getScale() {
        return scale;
    }

    public void setScale(Integer scale) {
        this.scale = scale;
    }

    public String getReceiveContent() {
        return receiveContent;
    }

    public void setReceiveContent(String receiveContent) {
        this.receiveContent = receiveContent;
    }
}
