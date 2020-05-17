package com.igomall.entity.course;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.common.BaseAttributeConverter;
import com.igomall.entity.BaseEntity;
import com.igomall.entity.OrderedEntity;

import javax.persistence.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "edu_course")
public class Course extends OrderedEntity<Long> {

    public final static String QUERY_ALL = "select id,title from edu_course order by orders asc,created_date desc ";


    @JsonView({JsonApiView.class,EditView.class,BaseEntity.ApiListView.class})
    private String title;

    private String path;

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OrderBy("order asc")
    private Set<Lesson> lessons = new HashSet<>();

    @OrderBy("order asc")
    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Folder> folders = new HashSet<>();

    @Column(length = 1000)
    @Convert(converter = Lesson.ProsConverter.class)
    @JsonView(BaseEntity.ApiListView.class)
    private Map<String,String> props = new HashMap<>();

    /**
     * 0: 待提交
     * 1： 待审核
     * 2： 审核通过
     * 3： 审核失败
     * 4：后台下架
     * 5：用户自己下架
     * 6：已删除
     */
    private Integer status;

    /**
     * 商品标签
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @OrderBy("order asc")
    private Set<CourseTag> courseTags = new HashSet<>();


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Set<Lesson> getLessons() {
        return lessons;
    }

    public Map<String, String> getProps() {
        return props;
    }

    public void setProps(Map<String, String> props) {
        this.props = props;
    }

    public void setLessons(Set<Lesson> lessons) {
        this.lessons = lessons;
    }

    public Set<Folder> getFolders() {
        return folders;
    }

    public void setFolders(Set<Folder> folders) {
        this.folders = folders;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Set<CourseTag> getCourseTags() {
        return courseTags;
    }

    public void setCourseTags(Set<CourseTag> courseTags) {
        this.courseTags = courseTags;
    }

    public interface IndexView extends ListView{

    }

    @Converter
    public static class ProsConverter extends BaseAttributeConverter<Map<String,String>> {
    }
}
