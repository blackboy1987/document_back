package com.igomall.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
    public class MessageResponse implements Serializable {

    @JsonProperty("TopicOwner")
    private String topicOwner;

    @JsonProperty("Subscriber")
    private String subscriber;

    @JsonProperty("PublishTime")
    private Long publishTime;

    @JsonProperty("SubscriptionName")
    private String subscriptionName;

    @JsonProperty("messageMD5")
    private String MessageMD5;

    @JsonProperty("topicName")
    private String TopicName;

    @JsonProperty("MessageId")
    private String messageId;

    @JsonProperty("Message")
    private String message;

    public String getTopicOwner() {
        return topicOwner;
    }

    public void setTopicOwner(String topicOwner) {
        this.topicOwner = topicOwner;
    }

    public String getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(String subscriber) {
        this.subscriber = subscriber;
    }

    public Long getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Long publishTime) {
        this.publishTime = publishTime;
    }

    public String getSubscriptionName() {
        return subscriptionName;
    }

    public void setSubscriptionName(String subscriptionName) {
        this.subscriptionName = subscriptionName;
    }

    public String getMessageMD5() {
        return MessageMD5;
    }

    public void setMessageMD5(String messageMD5) {
        MessageMD5 = messageMD5;
    }

    public String getTopicName() {
        return TopicName;
    }

    public void setTopicName(String topicName) {
        TopicName = topicName;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}