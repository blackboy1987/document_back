package com.igomall.wechat.entity.material;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Article implements Serializable {

    /**
     *标题
     */
    @JsonProperty("title")
    private String title;

    /**
     *图文消息的封面图片素材id（必须是永久 media_ID）
     */
    @JsonProperty("thumb_media_id")
    private String thumbMediaId;

    /**
     *作者
     */
    @JsonProperty("author")
    private String author;

    /**
     *图文消息的摘要，仅有单图文消息才有摘要，多图文此处为空
     */
    @JsonProperty("digest")
    private String digest;

    /**
     *是否显示封面，0为false，即不显示，1为true，即显示
     */
    @JsonProperty("show_cover_pic")
    private Integer showCoverPic;

    /**
     *图文消息的具体内容，支持HTML标签，必须少于2万字符，小于1M，且此处会去除JS
     */
    @JsonProperty("content")
    private String content;

    /**
     *图文消息的原文地址，即点击“阅读原文”后的URL
     */
    @JsonProperty("content_source_url")
    private String contentSourceUrl;

    /**
     *是否打开评论，0不打开，1打开
     */
    @JsonProperty("need_open_comment")
    private Integer needOpenComment;

    /**
     *是否粉丝才可评论，0所有人可评论，1粉丝才可评论
     */
    @JsonProperty("only_fans_can_comment")
    private Integer onlyFansCanComment;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbMediaId() {
        return thumbMediaId;
    }

    public void setThumbMediaId(String thumbMediaId) {
        this.thumbMediaId = thumbMediaId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public Integer getShowCoverPic() {
        return showCoverPic;
    }

    public void setShowCoverPic(Integer showCoverPic) {
        this.showCoverPic = showCoverPic;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentSourceUrl() {
        return contentSourceUrl;
    }

    public void setContentSourceUrl(String contentSourceUrl) {
        this.contentSourceUrl = contentSourceUrl;
    }

    public Integer getNeedOpenComment() {
        return needOpenComment;
    }

    public void setNeedOpenComment(Integer needOpenComment) {
        this.needOpenComment = needOpenComment;
    }

    public Integer getOnlyFansCanComment() {
        return onlyFansCanComment;
    }

    public void setOnlyFansCanComment(Integer onlyFansCanComment) {
        this.onlyFansCanComment = onlyFansCanComment;
    }
}
