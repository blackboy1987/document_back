package com.igomall.wechat.entity.send;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NewsMessage extends BaseMessage {

    /**
     *图文消息个数；当用户发送文本、图片、视频、图文、地理位置这五种消息时，开发者只能回复1条图文消息；其余场景最多可回复8条图文消息
     */
    @JacksonXmlProperty(localName="ArticleCount")
    private Integer articleCount;

    /**
     *图文消息信息，注意，如果图文数超过限制，则将只发限制内的条数
     */
    @JacksonXmlProperty(localName="Articles")
    private List<Article> articles = new ArrayList<>();

    public Integer getArticleCount() {
        return articleCount;
    }

    public void setArticleCount(Integer articleCount) {
        this.articleCount = articleCount;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public static class Article implements Serializable{

        /**
         *图文消息标题
         */
        private String title;

        /**
         *图文消息描述
         */
        private String description;

        /**
         *图片链接，支持JPG、PNG格式，较好的效果为大图360*200，小图200*200
         */
        private String picUrl;

        /**
         *点击图文消息跳转链接
         */
        private String url;

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
        @JacksonXmlProperty(localName="PicUrl")
        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        @JacksonXmlCData
        @JacksonXmlProperty(localName="Url")
        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
