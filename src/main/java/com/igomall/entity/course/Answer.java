package com.igomall.entity.course;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.entity.BaseEntity;
import com.igomall.entity.OrderedEntity;
import com.igomall.entity.member.Member;
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
 * 章节
 */
@Entity
@Table(name = "edu_answer")
public class Answer extends BaseEntity<Long> {


    @JsonView({ListView.class})
    @Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
    @Length(max = 100)
    @Pattern(regexp = "^[0-9a-zA-Z_-]+$")
    @Column(nullable = false, updatable = false, unique = true)
    private String sn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false,updatable = false)
    private Course course;

    @NotEmpty
    @Length(max = 255)
    @Column(nullable = false)
    @JsonView({ListView.class})
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private Lesson lesson;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false,updatable = false)
    private Member member;

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

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    @Transient
    public String getUsername(){
        if(member!=null){
            return member.getUsername();
        }
        return null;
    }

    @Transient
    @JsonView({ListView.class})
    public String getCourseTitle(){
        if(course!=null){
            return course.getTitle();
        }
        return null;
    }

    @Transient
    @JsonView({ListView.class})
    public String getLessontitle(){
        if(lesson!=null){
            return lesson.getTitle();
        }
        return null;
    }


    public interface ListView extends BaseView{}
}
