package com.igomall.wechat.entity.receive;

/**
 * 接受的语音消息
 */
public class VoiceMessage extends BaseMessage {

    /**
     *语音格式，如amr，speex等
     */
    private String Format;

    /**
     *语音消息媒体id，可以调用获取临时素材接口拉取数据。
     */
    private String mediaId;

    public String getFormat() {
        return Format;
    }

    public void setFormat(String format) {
        Format = format;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }
}
