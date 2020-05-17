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
@Table(name = "edu_book_item")
public class BookItem extends OrderedEntity<Long> {

    public static final String QUERY_LIST = "select id,name,icon,site_url siteUrl,download_url downloadUrl,memo from edu_book_item where is_publication=true and book_category_id=? order by orders asc, created_date desc";


    @NotEmpty
    @Column(nullable = false)
    @JsonView({JsonApiView.class,ListView.class,EditView.class})
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
    @JsonView({JsonApiView.class,EditView.class})
    private String downloadUrl;

    @Length(max = 500)
    @Column(length = 500)
    @JsonView({JsonApiView.class,EditView.class, ListView.class})
    private String memo;

    @ManyToOne(fetch = FetchType.LAZY)
    private BookCategory bookCategory;

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

    public BookCategory getBookCategory() {
        return bookCategory;
    }

    public void setBookCategory(BookCategory bookCategory) {
        this.bookCategory = bookCategory;
    }

    @Transient
    @JsonView({ListView.class})
    public String getCategoryNname(){
        if(bookCategory!=null){
            if(bookCategory.getParent()!=null){
               return bookCategory.getParent().getName()+"/"+bookCategory.getName();
            }
            return bookCategory.getName();
        }
        return null;
    }

    @Transient
    @JsonView({EditView.class})
    public Long getCategoryId(){
        if(bookCategory!=null){
            return bookCategory.getId();
        }
        return null;
    }
}
