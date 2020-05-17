package com.igomall.wechat.entity.material;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.igomall.entity.BaseEntity;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name="edu_wechat_news_material_item")
public class NewsMaterialItem extends BaseEntity<Long> {

    @ManyToOne(fetch = FetchType.LAZY)
    private NewsMaterialContent newsMaterialContent;

    private String title;

    private String author;

    private String digest;

    @Lob
    private String content;

    @JsonProperty("content_source_url")
    private String contentSourceUrl;

    @JsonProperty("thumb_media_id")
    private String thumbMediaId;

    @JsonProperty("show_cover_pic")
    private Integer showCoverPic;

    private String url;

    @JsonProperty("thumb_url")
    private String thumbUrl;

    @JsonProperty("need_open_comment")
    private Integer needOpenComment;

    @JsonProperty("only_fans_can_comment")
    private Integer onlyFansCanComment;

    @JsonProperty("update_time")
    private Date updateTime;

    public NewsMaterialContent getNewsMaterialContent() {
        return newsMaterialContent;
    }

    public void setNewsMaterialContent(NewsMaterialContent newsMaterialContent) {
        this.newsMaterialContent = newsMaterialContent;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getThumbMediaId() {
        return thumbMediaId;
    }

    public void setThumbMediaId(String thumbMediaId) {
        this.thumbMediaId = thumbMediaId;
    }

    public Integer getShowCoverPic() {
        return showCoverPic;
    }

    public void setShowCoverPic(Integer showCoverPic) {
        this.showCoverPic = showCoverPic;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @PrePersist
    public void preSave(){
        if(updateTime==null){
            updateTime = new Date();
        }
    }
}
