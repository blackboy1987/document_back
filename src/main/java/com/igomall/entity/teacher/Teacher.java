package com.igomall.entity.teacher;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.entity.OrderedEntity;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "edu_teacher")
public class Teacher extends OrderedEntity<Long> {

    /**
     * 姓名
     */
    @NotEmpty
    @Length(max = 10)
    @Column(nullable = false,length = 10)
    @JsonView({ListView.class,EditView.class})
    private String name;

    /**
     * 讲师介绍
     */
    @Length(max = 500)
    @Column(nullable = false,length = 500)
    @JsonView({EditView.class})
    private String introduction;

    /**
     * 讲师等级
     * 1： 高级讲师
     * 1： 首席讲师
     */
    @NotNull
    @Column(nullable = false)
    @JsonView({ListView.class,EditView.class})
    private Integer level;

    /**
     * 头像
     */
    @Length(max = 300)
    @Column(length = 300)
    @JsonView({ListView.class,EditView.class})
    private String avatar;

    /**
     * 是否可用
     */
    @NotNull
    @Column(nullable = false)
    @JsonView({ListView.class,EditView.class})
    private Boolean isEnabled;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Boolean getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public interface ListView extends BaseView {}
    public interface EditView extends IdView {}
}
