package com.igomall.wechat.entity.receive;

/**
 * 接受的图片消息
 */
public class ImageMessage extends BaseMessage {

    /**
     *图片消息媒体id，可以调用获取临时素材接口拉取数据。
     */
    private String picUrl;

    /**
     *图片链接（由系统生成）
     */
    private String mediaId;

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }
}
