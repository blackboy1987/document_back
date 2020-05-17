package com.igomall.wechat.entity.send;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.io.Serializable;

public class MusicMessage extends BaseMessage {

    @JacksonXmlProperty(localName="Music")
    private Music voice;

    public Music getVoice() {
        return voice;
    }

    public void setVoice(Music voice) {
        this.voice = voice;
    }

    public static class Music implements Serializable{

        /**
         *音乐标题
         */
        private String title;
        /**
         *音乐描述
         */
        private String description;
        /**
         *音乐链接
         */
        private String musicUrl;
        /**
         *高质量音乐链接，WIFI环境优先使用该链接播放音乐
         */
        private String hqMusicUrl;
        /**
         *缩略图的媒体id，通过素材管理中的接口上传多媒体文件，得到的id
         */
        private String thumbMediaId;

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

        @JacksonXmlCData
        @JacksonXmlProperty(localName="MusicUrl")
        public String getMusicUrl() {
            return musicUrl;
        }

        public void setMusicUrl(String musicUrl) {
            this.musicUrl = musicUrl;
        }

        @JacksonXmlCData
        @JacksonXmlProperty(localName="HQMusicUrl")
        public String getHqMusicUrl() {
            return hqMusicUrl;
        }

        public void setHqMusicUrl(String hqMusicUrl) {
            this.hqMusicUrl = hqMusicUrl;
        }

        @JacksonXmlCData
        @JacksonXmlProperty(localName="ThumbMediaId")
        public String getThumbMediaId() {
            return thumbMediaId;
        }

        public void setThumbMediaId(String thumbMediaId) {
            this.thumbMediaId = thumbMediaId;
        }
    }
}
