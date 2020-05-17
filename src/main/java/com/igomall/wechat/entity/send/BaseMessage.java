package com.igomall.wechat.entity.send;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * 微信用户向公众账号发消息的基类
 */
public class BaseMessage {

    /**
     *接收方帐号（收到的OpenID）
     */
    @JacksonXmlCData
    @JacksonXmlProperty(localName="ToUserName")
    private String toUserName;

    /**
     *开发者微信号
     */
    @JacksonXmlCData
    @JacksonXmlProperty(localName="FromUserName")
    private String fromUserName;

    /**
     *消息创建时间 （整型）
     */
    @JacksonXmlProperty(localName="CreateTime")
    private Long createTime;

    /**
     *消息类型
     */
    @JacksonXmlCData
    @JacksonXmlProperty(localName="MsgType")
    private MsgType msgType;

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

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public MsgType getMsgType() {
        return msgType;
    }

    public void setMsgType(MsgType msgType) {
        this.msgType = msgType;
    }
}
