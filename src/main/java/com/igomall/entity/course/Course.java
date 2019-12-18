package com.igomall.entity.course;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.entity.OrderedEntity;
import com.igomall.entity.teacher.Teacher;
import org.apache.commons.lang3.ArrayUtils;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;
import org.apache.commons.lang3.ArrayUtils;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 * 课程
 */
@Entity
@Table(name = "edu_course")
public class Course extends OrderedEntity<Long> {

    @JsonView(BaseView.class)
    @Field(store = Store.YES, index = Index.NO, analyze = Analyze.NO)
    @Length(max = 100)
    @Pattern(regexp = "^[0-9a-zA-Z_-]+$")
    @Column(nullable = false, updatable = false, unique = true)
    private String sn;

    @OneToMany(mappedBy = "course",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Set<Part> parts = new HashSet<>();

    @OneToMany(mappedBy = "course",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Set<Chapter> chapters = new HashSet<>();

    @OneToMany(mappedBy = "course",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Set<Lesson> lessons = new HashSet<>();

    @NotEmpty
    @Length(max = 100)
    @Column(nullable = false,length = 100)
    @JsonView({ListView.class,EditView.class,AllListView.class})
    private String title;


    @JsonView({EditView.class,EditView.class})
    private String memo;

    @Lob
    @JsonView({EditView.class})
    private String description;

    @NotEmpty
    @Length(max = 400)
    @Column(nullable = false,length = 400)
    @JsonView({ListView.class,EditView.class})
    private String image;

    /**
     * 商品分类
     */
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private CourseCategory courseCategory;

    /**
     * 商品标签
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @OrderBy("order asc")
    private Set<CourseTag> courseTags = new HashSet<>();

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Teacher teacher;

    public Set<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(Set<Lesson> lessons) {
        this.lessons = lessons;
    }

    /**
     * 获取编号
     *
     * @return 编号
     */
    public String getSn() {
        return sn;
    }

    /**
     * 设置编号
     *
     * @param sn
     *            编号
     */
    public void setSn(String sn) {
        this.sn = sn;
    }

    public Set<Part> getParts() {
        return parts;
    }

    public void setParts(Set<Part> parts) {
        this.parts = parts;
    }

    public Set<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(Set<Chapter> chapters) {
        this.chapters = chapters;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public CourseCategory getCourseCategory() {
        return courseCategory;
    }

    public void setCourseCategory(CourseCategory courseCategory) {
        this.courseCategory = courseCategory;
    }

    public Set<CourseTag> getCourseTags() {
        return courseTags;
    }

    public void setCourseTags(Set<CourseTag> courseTags) {
        this.courseTags = courseTags;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    @Transient
    @JsonView({ListView.class})
    public String getTeacherName(){
        if(teacher!=null){
            return teacher.getName();
        }
        return null;
    }

    @Transient
    @JsonView({EditView.class})
    public Long getTeacherId(){
        if(teacher!=null){
            return teacher.getId();
        }
        return null;
    }

    @Transient
    @JsonView({ListView.class})
    public String getCourseCategoryName(){
        if(courseCategory!=null){
            return courseCategory.getName();
        }
        return null;
    }

    @Transient
    @JsonView({EditView.class})
    public Long[] getCourseCategoryIds(){
        if(courseCategory!=null){
            return ArrayUtils.add(courseCategory.getParentIds(),courseCategory.getId());
        }
        return null;
    }


    public interface ListView extends BaseView{}
    public interface EditView extends IdView{}
    public interface AllListView extends IdView{}
}
