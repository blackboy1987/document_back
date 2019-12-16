package com.igomall.entity.course;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.entity.OrderedEntity;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
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
    @JoinColumn(nullable = false,updatable = false)
    private Part part;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false,updatable = false)
    private Course course;

    @NotEmpty
    @Length(max = 255)
    @Column(length = 255,nullable = false)
    @JsonView({ListView.class,EditView.class,AllList.class})
    private String title;

    @OneToMany(mappedBy = "chapter",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Set<Lesson> lessons = new HashSet<>();


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
}
