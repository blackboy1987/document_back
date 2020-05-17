package com.igomall.wechat.entity.material;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.igomall.entity.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Entity
@Table(name="edu_wechat_news_material")
public class NewsMaterial extends BaseEntity<Long> {

    @JsonProperty("media_id")
    @NotEmpty
    @Column(nullable = false,updatable = false,unique = true)
    private String mediaId;

    @JsonProperty("update_time")
    private Date updateTime;

    @OneToOne(mappedBy = "newsMaterial", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private NewsMaterialContent content;


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

    public NewsMaterialContent getContent() {
        return content;
    }

    public void setContent(NewsMaterialContent content) {
        this.content = content;
    }

    @PrePersist
    public void preSave(){
        if(updateTime==null){
            updateTime = new Date();
        }
    }
}
