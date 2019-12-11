package com.igomall.entity.course;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.entity.OrderedEntity;
import com.igomall.entity.course.Chapter;
import com.igomall.entity.course.Course;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "edu_part")
public class Part extends OrderedEntity<Long> {

    @NotEmpty
    @Length(max = 255)
    @Column(length = 255,nullable = false)
    @JsonView({ListView.class,EditView.class,AllList.class})
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    private Course course;

    @OneToMany(mappedBy = "part",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Set<Chapter> chapter = new HashSet<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Set<Chapter> getChapter() {
        return chapter;
    }

    public void setChapter(Set<Chapter> chapter) {
        this.chapter = chapter;
    }

    @JsonView({ListView.class,EditView.class})
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

    public interface ListView extends BaseView{}
    public interface EditView extends IdView{}

    public interface AllList extends IdView{}
}
