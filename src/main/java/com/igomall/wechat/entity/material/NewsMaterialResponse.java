package com.igomall.wechat.entity.material;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NewsMaterialResponse implements Serializable {

    @JsonProperty("item")
    private List<Material> materials = new ArrayList<>();

    @JsonProperty("total_count")
    private Integer totalCount;

    @JsonProperty("item_count")
    private Integer itemCount;

    public List<Material> getMaterials() {
        return materials;
    }

    public void setMaterials(List<Material> materials) {
        this.materials = materials;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getItemCount() {
        return itemCount;
    }

    public void setItemCount(Integer itemCount) {
        this.itemCount = itemCount;
    }


    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Material implements Serializable{

        @JsonProperty("media_id")
        private String mediaId;

        @JsonProperty("update_time")
        private Date updateTime;

        private String name;

        private String url;

        private List<String> tags = new ArrayList<>();

        @JsonProperty("content")
        private NewsMaterialContent newsMaterialContent;

        public String getMediaId() {
            return mediaId;
        }

        public void setMediaId(String mediaId) {
            this.mediaId = mediaId;
        }

        public Date getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Date updateTime) {
            this.updateTime = updateTime;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public List<String> getTags() {
            return tags;
        }

        public void setTags(List<String> tags) {
            this.tags = tags;
        }

        public NewsMaterialContent getNewsMaterialContent() {
            return newsMaterialContent;
        }

        public void setNewsMaterialContent(NewsMaterialContent newsMaterialContent) {
            this.newsMaterialContent = newsMaterialContent;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class NewsMaterialContent implements Serializable{

        @JsonProperty("news_item")
        private List<NewsMaterialItem> newsMaterialItems = new ArrayList<>();

        @JsonProperty("create_time")
        private Date createTime;

        @JsonProperty("update_time")
        private Date updateTime;

        public List<NewsMaterialItem> getNewsMaterialItems() {
            return newsMaterialItems;
        }

        public void setNewsMaterialItems(List<NewsMaterialItem> newsMaterialItems) {
            this.newsMaterialItems = newsMaterialItems;
        }

        public Date getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Date createTime) {
            this.createTime = createTime;
        }

        public Date getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Date updateTime) {
            this.updateTime = updateTime;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class NewsMaterialItem implements Serializable{

        private String title;

        private String author;

        private String digest;

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
    }
}
