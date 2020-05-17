package com.igomall.wechat.entity.send;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.io.Serializable;

public class VideoMessage extends BaseMessage {

    @JacksonXmlProperty(localName="Video")
    private Video video;

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public static class Video implements Serializable{

        /**
         *通过素材管理中的接口上传多媒体文件，得到的id
         */
        private String mediaId;
        /**
         *视频消息的标题
         */
        private String title;
        /**
         *视频消息的描述
         */
        private String description;

        @JacksonXmlCData
        @JacksonXmlProperty(localName="MediaId")
        public String getMediaId() {
            return mediaId;
        }

        public void setMediaId(String mediaId) {
            this.mediaId = mediaId;
        }

        @JacksonXmlCData
        @JacksonXmlProperty(localName="Title")
        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        @JacksonXmlCData
        @JacksonXmlProperty(localName="Description")
        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
