package com.igomall.wechat.entity.send;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.io.Serializable;

public class ImageMessage extends BaseMessage {

    @JacksonXmlProperty(localName="Image")
    private Image image;

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public static class Image implements Serializable{

        /**
         * 通过素材管理中的接口上传多媒体文件，得到的id。
         *
         */
        private String mediaId;

        @JacksonXmlCData
        @JacksonXmlProperty(localName="MediaId")
        public String getMediaId() {
            return mediaId;
        }

        public void setMediaId(String mediaId) {
            this.mediaId = mediaId;
        }
    }
}
