package com.igomall.entity.wechat;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.entity.OrderedEntity;
import com.igomall.entity.member.Member;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "edu_project_item_error")
public class ProjectItemError extends OrderedEntity<Long> {

    @NotEmpty
    @Column(nullable = false)
    @JsonView({ListView.class})
    private String name;

    @Length(max = 200)
    @Pattern(regexp = "^(?i)(http:\\/\\/|https:\\/\\/|\\/).*$")
    private String icon;

    @Length(max = 200)
    @JsonView({JsonApiView.class,EditView.class,ListView.class})
    private String siteUrl;

    @Length(max = 200)
    @JsonView({JsonApiView.class,EditView.class,ListView.class})
    private String downloadUrl;

    @Length(max = 500)
    @Column(length = 500)
    @JsonView({ListView.class})
    private String memo;

    @ManyToOne(fetch = FetchType.LAZY)
    private ProjectItem projectItem;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    /**
     * 是否发布
     */
    @NotNull
    @Column(nullable = false)
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

    public ProjectItem getProjectItem() {
        return projectItem;
    }

    public void setProjectItem(ProjectItem projectItem) {
        this.projectItem = projectItem;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    @Transient
    public Long getMemberId(){
        if(member!=null){
            return member.getId();
        }
        return null;
    }

    @Transient
    public String getUserName(){
        if(member!=null){
            return member.getUsername();
        }
        return null;
    }

    @Transient
    public Long getItemId(){
        if(projectItem!=null){
            return projectItem.getId();
        }
        return null;
    }

    @Transient
    @JsonView({ListView.class})
    public String getItemName(){
        if(projectItem!=null){
            return projectItem.getName();
        }
        return null;
    }
}
