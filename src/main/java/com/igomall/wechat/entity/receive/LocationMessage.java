package com.igomall.wechat.entity.receive;

/**
 * 接受的地理位置消息
 */
public class LocationMessage extends BaseMessage {

    /**
     *	地理位置维度
     */
    private Double locationX;

    /**
     *地理位置经度
     */
    private Double locationY;

    /**
     *地图缩放大小
     */
    private Integer scale;

    /**
     *地理位置信息
     */
    private String label;

    /**
     *视频消息媒体id，可以调用获取临时素材接口拉取数据。
     */
    private String mediaId;

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

    public Integer getScale() {
        return scale;
    }

    public void setScale(Integer scale) {
        this.scale = scale;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }
}
