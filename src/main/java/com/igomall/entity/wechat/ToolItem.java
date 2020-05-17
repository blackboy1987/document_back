package com.igomall.entity.wechat;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.entity.OrderedEntity;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "edu_tool_item")
public class ToolItem extends OrderedEntity<Long> {

    public static final String QUERY_LIST = "select id,name,icon,site_url siteUrl,download_url downloadUrl,memo from edu_tool_item where is_publication=true and tool_category_id=? order by orders asc, created_date desc";

    /**
     * 点击数缓存名称
     */
    public static final String HITS_CACHE_NAME = "toolItemHits";

    @NotEmpty
    @Column(nullable = false)
    @JsonView({JsonApiView.class,ListView.class,EditView.class,ApiListView.class})
    private String name;

    @Length(max = 200)
    @Pattern(regexp = "^(?i)(http:\\/\\/|https:\\/\\/|\\/).*$")
    @JsonView({JsonApiView.class,ListView.class,EditView.class})
    private String icon;

    @Length(max = 200)
    @Pattern(regexp = "^(?i)(http:\\/\\/|https:\\/\\/|\\/).*$")
    @JsonView({JsonApiView.class,EditView.class})
    private String siteUrl;

    @Length(max = 200)
    @Pattern(regexp = "^(?i)(http:\\/\\/|https:\\/\\/|\\/).*$")
    @JsonView({JsonApiView.class,ListView.class,EditView.class})
    private String downloadUrl;

    @NotNull
    @JsonView({ApiListView.class})
    @Column(nullable = false)
    private Long downloadHits;

    @Length(max = 500)
    @Column(length = 500)
    @JsonView({JsonApiView.class,EditView.class, ListView.class,ApiListView.class})
    private String memo;

    @ManyToOne(fetch = FetchType.LAZY)
    private ToolCategory toolCategory;

    /**
     * 是否发布
     */
    @Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
    @NotNull
    @Column(nullable = false)
    @JsonView({EditView.class, ListView.class})
    private Boolean isPublication;

    /**
     * 获取是否发布
     *
     * @return 是否发布
     */
    public Boolean getIsPublication() {
        return isPublication;
    }

    /**
     * 设置是否发布
     *
     * @param isPublication
     *            是否发布
     */
    public void setIsPublication(Boolean isPublication) {
        this.isPublication = isPublication;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getSiteUrl() {
        return siteUrl;
    }

    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public ToolCategory getToolCategory() {
        return toolCategory;
    }

    public void setToolCategory(ToolCategory toolCategory) {
        this.toolCategory = toolCategory;
    }
    public Long getDownloadHits() {
        return downloadHits;
    }

    public void setDownloadHits(Long downloadHits) {
        this.downloadHits = downloadHits;
    }

    @Transient
    @JsonView({ListView.class})
    public String getCategoryNname(){
        if(toolCategory!=null){
            if(toolCategory.getParent()!=null){
                return toolCategory.getParent().getName()+"/"+toolCategory.getName();
            }
            return toolCategory.getName();
        }
        return null;
    }

    @Transient
    @JsonView({EditView.class})
    public Long getCategoryId(){
        if(toolCategory!=null){
            return toolCategory.getId();
        }
        return null;
    }
}
