package com.igomall.entity.course;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.entity.BaseEntity;
import com.igomall.entity.member.Member;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "edu_course_comment")
public class CourseComment extends BaseEntity<Long> {
    /**
     * 类型
     */
    public enum Type {

        /**
         * 好评
         */
        positive,

        /**
         * 中评
         */
        moderate,

        /**
         * 差评
         */
        negative
    }

    /**
     * 评分
     */
    @NotNull
    @Min(1)
    @Max(5)
    @Column(nullable = false, updatable = false)
    @JsonView({ListView.class})
    private Integer score;

    /**
     * 内容
     */
    @NotEmpty
    @Length(max = 200)
    @Column(nullable = false, updatable = false)
    @JsonView({ListView.class})
    private String content;

    /**
     * 是否显示
     */
    @Column(nullable = false)
    private Boolean isShow;

    /**
     * IP
     */
    @Column(nullable = false, updatable = false)
    private String ip;

    /**
     * 会员
     */
    @JsonView(BaseView.class)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private Member member;

    /**
     * 商品
     */
    @JsonView(BaseView.class)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private Course course;

    /**
     * 店铺
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Lesson lesson;

    /**
     * 回复
     */
    @JsonView(ListView.class)
    @OneToMany(mappedBy = "forReview", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @OrderBy("createdDate asc")
    private Set<CourseComment> replyReviews = new HashSet<>();

    /**
     * 评论
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(updatable = false)
    private CourseComment forReview;

    /**
     * 获取评分
     *
     * @return 评分
     */
    public Integer getScore() {
        return score;
    }

    /**
     * 设置评分
     *
     * @param score
     *            评分
     */
    public void setScore(Integer score) {
        this.score = score;
    }

    /**
     * 获取内容
     *
     * @return 内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置内容
     *
     * @param content
     *            内容
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取是否显示
     *
     * @return 是否显示
     */
    public Boolean getIsShow() {
        return isShow;
    }

    /**
     * 设置是否显示
     *
     * @param isShow
     *            是否显示
     */
    public void setIsShow(Boolean isShow) {
        this.isShow = isShow;
    }

    /**
     * 获取IP
     *
     * @return IP
     */
    public String getIp() {
        return ip;
    }

    /**
     * 设置IP
     *
     * @param ip
     *            IP
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * 获取会员
     *
     * @return 会员
     */
    public Member getMember() {
        return member;
    }

    /**
     * 设置会员
     *
     * @param member
     *            会员
     */
    public void setMember(Member member) {
        this.member = member;
    }

    /**
     * 获取商品
     *
     * @return 商品
     */
    public Course getCourse() {
        return course;
    }

    /**
     * 设置商品
     *
     * @param course
     *            商品
     */
    public void setCourse(Course course) {
        this.course = course;
    }

    /**
     * 获取店铺
     *
     * @return 店铺
     */
    public Lesson getLesson() {
        return lesson;
    }

    /**
     * 设置店铺
     *
     * @param lesson
     *            店铺
     */
    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    /**
     * 获取回复
     *
     * @return 回复
     */
    public Set<CourseComment> getReplyReviews() {
        return replyReviews;
    }

    /**
     * 设置回复
     *
     * @param replyReviews
     *            回复
     */
    public void setReplyReviews(Set<CourseComment> replyReviews) {
        this.replyReviews = replyReviews;
    }

    /**
     * 获取评论
     *
     * @return 评论
     */
    public CourseComment getForReview() {
        return forReview;
    }

    /**
     * 设置评论
     *
     * @param forReview
     *            评论
     */
    public void setForReview(CourseComment forReview) {
        this.forReview = forReview;
    }

    @Transient
    @JsonView({ListView.class})
    public String getUsername(){
        if(member!=null){
            return member.getUsername();
        }
        return null;
    }

    @Transient
    @JsonView({ListView.class})
    public String getAvatar(){
        if(member!=null){
            return member.getAvatar();
        }
        return null;
    }


    public interface ListView extends IdView{}
}
