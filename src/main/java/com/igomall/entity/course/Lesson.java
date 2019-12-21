package com.igomall.entity.course;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.entity.OrderedEntity;
import com.igomall.entity.teacher.Teacher;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.HashSet;
import java.util.Set;

/**
 * 章节的视频
 */
@Entity
@Table(name = "edu_lesson")
public class Lesson extends OrderedEntity<Long> {

    @JsonView(BaseView.class)
    @Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
    @Length(max = 100)
    @Pattern(regexp = "^[0-9a-zA-Z_-]+$")
    @Column(nullable = false, updatable = false, unique = true)
    private String sn;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(nullable = false,updatable = false)
    private Chapter chapter;


    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(nullable = false,updatable = false)
    private Course course;

    @NotEmpty
    @Length(max = 100)
    @Column(nullable = false,length = 100)
    @JsonView({ListView.class,EditView.class})
    private String title;

    @NotEmpty
    @Column(nullable = false,length = 500)
    @JsonView({ListView.class,EditView.class})
    private String videoUrl;

    @NotEmpty
    @Column(nullable = false,length = 500)
    @JsonView({ListView.class,EditView.class})
    private String videoImage;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Teacher teacher;

    /**
     * 评论
     */
    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<CourseComment> courseComments = new HashSet<>();

    /**
     * 咨询
     */
    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<CourseConsultation> courseConsultations = new HashSet<>();


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

    public Chapter getChapter() {
        return chapter;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
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

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getVideoImage() {
        return videoImage;
    }

    public void setVideoImage(String videoImage) {
        this.videoImage = videoImage;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Set<CourseComment> getCourseComments() {
        return courseComments;
    }

    public void setCourseComments(Set<CourseComment> courseComments) {
        this.courseComments = courseComments;
    }

    public Set<CourseConsultation> getCourseConsultations() {
        return courseConsultations;
    }

    public void setCourseConsultations(Set<CourseConsultation> courseConsultations) {
        this.courseConsultations = courseConsultations;
    }

    @JsonView({ListView.class})
    public String getChapterTitle(){
        if(chapter!=null){
            return chapter.getTitle();
        }
        return null;
    }

    @JsonView({EditView.class})
    public Long getChapterId(){
        if(chapter!=null){
            return chapter.getId();
        }
        return null;
    }

    @JsonView({ListView.class})
    public String getCourseTitle(){
        if(chapter!=null){
            return chapter.getCourseTitle();
        }
        return null;
    }

    @JsonView({EditView.class})
    public Long getCourseId(){
        if(chapter!=null){
            return chapter.getCourseId();
        }
        return null;
    }


    public interface ListView extends BaseView{}
    public interface EditView extends IdView{}
}
