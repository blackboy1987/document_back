package com.igomall.entity;

import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Configurable;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

/**
 * 章节
 */
@Entity
@Table(name = "edu_chapter")
public class Chapter extends OrderedEntity<Long> {

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
