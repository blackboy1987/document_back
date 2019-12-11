package com.igomall.entity.course;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.entity.OrderedEntity;
import com.igomall.entity.course.Chapter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

/**
 * 章节的视频
 */
@Entity
@Table(name = "edu_lesson")
public class Lesson extends OrderedEntity<Long> {

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(nullable = false,updatable = false)
    private Chapter chapter;

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


    public Chapter getChapter() {
        return chapter;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
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


    public interface ListView extends BaseView{}
    public interface EditView extends IdView{}
}
