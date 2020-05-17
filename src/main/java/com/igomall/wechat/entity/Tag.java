package com.igomall.wechat.entity;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.entity.BaseEntity;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 用户标签
 */
@Entity
@Table(name = "edu_wechat_user_tag")
public class Tag extends BaseEntity<Long> {

    @NotEmpty
    @Column(nullable = false,unique = true)
    @JsonView({ListView.class,EditView.class})
    private String name;

    @Column(nullable = false)
    @Min(0)
    @JsonView({ListView.class,EditView.class})
    private Long count;

    @Length(max = 400)
    @Column(length = 400)
    @JsonView({ListView.class,EditView.class})
    private String memo;

    @JsonView({ListView.class,EditView.class})
    @Column(nullable = false)
    private Boolean isEnabled;

    @Column(nullable = false,updatable = false,unique = true)
    private Long weChatId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Boolean getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public Long getWeChatId() {
        return weChatId;
    }

    public void setWeChatId(Long weChatId) {
        this.weChatId = weChatId;
    }
}
