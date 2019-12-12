package com.igomall.entity.teacher;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.entity.OrderedEntity;
import com.igomall.entity.course.Course;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "edu_teacher")
public class Teacher extends OrderedEntity<Long> {

    /**
     * 姓名
     */
    @NotEmpty
    @Length(max = 10)
    @Column(nullable = false,length = 10)
    @JsonView({ListView.class,EditView.class,AllView.class})
    private String name;

    /**
     * 讲师介绍
     */
    @Length(max = 500)
    @Column(nullable = false,length = 500)
    @JsonView({EditView.class,ListView.class})
    private String introduction;

    /**
     * 讲师等级
     * 1： 高级讲师
     * 1： 首席讲师
     */
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private TeacherRank teacherRank;

    /**
     * 头像
     */
    @Length(max = 300)
    @Column(length = 300)
    @JsonView({ListView.class,EditView.class})
    private String avatar;

    @OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY)
    private Set<Course> courses = new HashSet<>();

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

    public TeacherRank getTeacherRank() {
        return teacherRank;
    }

    public void setTeacherRank(TeacherRank teacherRank) {
        this.teacherRank = teacherRank;
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

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    @JsonView({EditView.class})
    public Long getTeacherRankId(){
        if(teacherRank!=null){
            return teacherRank.getId();
        }
        return null;
    }

    @JsonView({ListView.class})
    public String getTeacherRankName(){
        if(teacherRank!=null){
            return teacherRank.getName();
        }
        return null;
    }

    public interface ListView extends BaseView {}
    public interface EditView extends IdView {}
    public interface AllView extends IdView{}
}
