package com.igomall.wechat.entity.material;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.igomall.entity.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="edu_wechat_news_material_content")
public class NewsMaterialContent extends BaseEntity<Long> {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(updatable = false)
    private NewsMaterial newsMaterial;

    @JsonProperty("news_item")
    @OneToMany(mappedBy = "newsMaterialContent",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<NewsMaterialItem> newsMaterialItems = new ArrayList<>();

    @JsonProperty("create_time")
    private Date createTime;

    @JsonProperty("update_time")
    private Date updateTime;

    public NewsMaterial getNewsMaterial() {
        return newsMaterial;
    }

    public void setNewsMaterial(NewsMaterial newsMaterial) {
        this.newsMaterial = newsMaterial;
    }

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
