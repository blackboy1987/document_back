package com.igomall.entity.activity;

import com.igomall.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "edu_share_url")
public class ShareUrl extends BaseEntity<Long> {

    @NotEmpty
    @Column(updatable = false,nullable = false,unique = true)
    private String url;

    private String title;

    /**
     * 0:可领取
     * 1：不可领取
     */
    private Integer status;

    private Long readCount;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getReadCount() {
        return readCount;
    }

    public void setReadCount(Long readCount) {
        this.readCount = readCount;
    }
}
