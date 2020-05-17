package com.igomall.entity.member;

import com.igomall.entity.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "edu_lesson_read_record")
public class LessonReadRecord extends BaseEntity<Long> {
    private Long lessonId;

    private String playUrlName;

    private String playUrlUrl;

    private Long memberId;


    public Long getLessonId() {
        return lessonId;
    }

    public void setLessonId(Long lessonId) {
        this.lessonId = lessonId;
    }

    public String getPlayUrlName() {
        return playUrlName;
    }

    public void setPlayUrlName(String playUrlName) {
        this.playUrlName = playUrlName;
    }

    public String getPlayUrlUrl() {
        return playUrlUrl;
    }

    public void setPlayUrlUrl(String playUrlUrl) {
        this.playUrlUrl = playUrlUrl;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }
}
