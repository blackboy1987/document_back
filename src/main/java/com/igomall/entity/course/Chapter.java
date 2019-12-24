package com.igomall.entity.course;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.entity.OrderedEntity;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;
import com.igomall.entity.teacher.Teacher;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 * 章节
 */
@Entity
@Table(name = "edu_chapter")
public class Chapter extends OrderedEntity<Long> {


    @JsonView(BaseView.class)
    @Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
    @Length(max = 100)
    @Pattern(regexp = "^[0-9a-zA-Z_-]+$")
    @Column(nullable = false, updatable = false, unique = true)
    private String sn;

    @ManyToOne(fetch = FetchType.LAZY)
    private Part part;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false,updatable = false)
    private Course course;

    @NotEmpty
    @Length(max = 255)
    @Column(nullable = false)
    @JsonView({ListView.class,EditView.class,AllList.class,AllView.class})
    private String title;

    @Length(max = 1000)
    @Column(length = 1000)
    @JsonView({ListView.class,EditView.class,AllList.class,AllView.class})
    private String description;

    @OneToMany(mappedBy = "chapter",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JsonView({AllView.class})
    private Set<Lesson> lessons = new HashSet<>();

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Teacher teacher;

    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        this.part = part;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(Set<Lesson> lessons) {
        this.lessons = lessons;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    @JsonView({Part.ListView.class, EditView.class})
    public Long getCourseId(){
        if(course!=null){
            return course.getId();
        }
        return null;
    }

    @JsonView({ListView.class})
    public String getCourseTitle(){
        if(course!=null){
            return course.getTitle();
        }
        return null;
    }

    @JsonView({Part.ListView.class, EditView.class})
    public Long getTeacherId(){
        if(teacher!=null){
            return teacher.getId();
        }
        return null;
    }

    @JsonView({ListView.class})
    public String getTeacherName(){
        if(teacher!=null){
            return teacher.getName();
        }
        return null;
    }

    @JsonView({ListView.class})
    public String getPartTitle(){
        if(part!=null){
            return part.getTitle();
        }
        return null;
    }

    @JsonView({ListView.class, EditView.class})
    public Long getPartId(){
        if(part!=null){
            return part.getId();
        }
        return null;
    }

    public interface ListView extends BaseView{}
    public interface EditView extends IdView{}
    public interface AllList extends IdView{}
    public interface AllView extends IdView{}
}
