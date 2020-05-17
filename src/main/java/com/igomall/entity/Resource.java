package com.igomall.entity;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.common.BaseAttributeConverter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "document_resource")
public class Resource extends BaseEntity<Long> {

    /**
     * 点击数缓存名称
     */
    public static final String HITS_CACHE_NAME = "resourceHits";

    @Column(nullable = false,unique = true)
    @JsonView({ApiListView.class})
    private String name;

    @Convert(converter = ResUrl.class)
    @Column(length = 2000)
    private List<String> resUrls = new ArrayList<>();

    @NotNull
    @JsonView({ApiListView.class})
    @Column(nullable = false)
    private Long downloadHits;

    private String img;

    private String memo;

    /**
     * 商品标签
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @OrderBy("order asc")
    private Set<ResourceTag> resourceTags = new HashSet<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getResUrls() {
        return resUrls;
    }

    public void setResUrls(List<String> resUrls) {
        this.resUrls = resUrls;
    }

    public Long getDownloadHits() {
        return downloadHits;
    }

    public void setDownloadHits(Long downloadHits) {
        this.downloadHits = downloadHits;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    /**
     * 获取商品标签
     *
     * @return 商品标签
     */
    public Set<ResourceTag> getResourceTags() {
        return resourceTags;
    }

    /**
     * 设置商品标签
     *
     * @param resourceTags
     *            商品标签
     */
    public void setResourceTags(Set<ResourceTag> resourceTags) {
        this.resourceTags = resourceTags;
    }


    @Converter
    public static class ResUrl extends BaseAttributeConverter<List<String>> {

    }
}
