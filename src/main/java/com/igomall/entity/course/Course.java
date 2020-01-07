package com.igomall.entity.course;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.common.BigDecimalNumericFieldBridge;
import com.igomall.entity.OrderedEntity;
import com.igomall.entity.teacher.Teacher;
import org.apache.commons.lang3.ArrayUtils;
import org.hibernate.search.annotations.*;
import org.hibernate.search.annotations.Index;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 课程
 */
@Entity
@Table(name = "edu_course")
public class Course extends OrderedEntity<Long> {

    /**
     * 点击数缓存名称
     */
    public static final String HITS_CACHE_NAME = "coursetHits";

    /**
     * 属性值属性个数
     */
    public static final int ATTRIBUTE_VALUE_PROPERTY_COUNT = 20;

    /**
     * 属性值属性名称前缀
     */
    public static final String ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX = "attributeValue";

    @JsonView({BaseView.class,IdView.class,CommonView.class})
    @Field(store = Store.YES, index = Index.NO, analyze = Analyze.NO)
    @Length(max = 100)
    @Pattern(regexp = "^[0-9a-zA-Z_-]+$")
    @Column(nullable = false, updatable = false, unique = true)
    private String sn;

    @OneToMany(mappedBy = "course",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Set<Part> parts = new HashSet<>();

    @OneToMany(mappedBy = "course",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Set<Chapter> chapters = new HashSet<>();

    @OneToMany(mappedBy = "course",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Set<Lesson> lessons = new HashSet<>();

    @NotEmpty
    @Length(max = 100)
    @Column(nullable = false,length = 100)
    @JsonView({ListView.class,EditView.class,AllListView.class,WebView.class,CommonView.class})
    private String title;


    @JsonView({EditView.class,EditView.class})
    private String memo;

    @Lob
    @JsonView({EditView.class})
    private String description;

    @NotEmpty
    @Length(max = 400)
    @Column(nullable = false,length = 400)
    @JsonView({ListView.class,EditView.class,WebView.class,CommonView.class})
    private String image;

    /**
     * 销售价
     */
    @Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
    @NumericField
    @FieldBridge(impl = BigDecimalNumericFieldBridge.class)
    @Column(nullable = false, precision = 21, scale = 6)
    @JsonView({BaseView.class,WebView.class,CommonView.class})
    private BigDecimal price;

    /**
     * 商品分类
     */
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private CourseCategory courseCategory;

    /**
     * 商品标签
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @OrderBy("order asc")
    private Set<CourseTag> courseTags = new HashSet<>();

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

    public Set<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(Set<Lesson> lessons) {
        this.lessons = lessons;
    }

    /**
     * 搜索关键词
     */
    @Field(store = Store.YES, index = Index.YES, analyze = Analyze.YES)
    @Boost(1.5F)
    @Length(max = 200)
    private String keyword;

    /**
     * 评分
     */
    @Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
    @NumericField
    @Column(nullable = false, precision = 12, scale = 6)
    private Float score;

    /**
     * 总评分
     */
    @Column(nullable = false)
    private Long totalScore;

    /**
     * 评分数
     */
    @Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
    @NumericField
    @Column(nullable = false)
    private Long scoreCount;

    /**
     * 周点击数
     */
    @Column(nullable = false)
    private Long weekHits;

    /**
     * 月点击数
     */
    @Column(nullable = false)
    private Long monthHits;

    /**
     * 点击数
     */
    @Column(nullable = false)
    private Long hits;

    /**
     * 周销量
     */
    @Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
    @NumericField
    @Column(nullable = false)
    private Long weekSales;

    /**
     * 月销量
     */
    @Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
    @NumericField
    @Column(nullable = false)
    private Long monthSales;

    /**
     * 销量
     */
    @Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
    @NumericField
    @Column(nullable = false)
    private Long sales;

    /**
     * 周点击数更新日期
     */
    @Column(nullable = false)
    private Date weekHitsDate;

    /**
     * 月点击数更新日期
     */
    @Column(nullable = false)
    private Date monthHitsDate;

    /**
     * 周销量更新日期
     */
    @Column(nullable = false)
    private Date weekSalesDate;

    /**
     * 月销量更新日期
     */
    @Column(nullable = false)
    private Date monthSalesDate;

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

    public Set<Part> getParts() {
        return parts;
    }

    public void setParts(Set<Part> parts) {
        this.parts = parts;
    }

    public Set<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(Set<Chapter> chapters) {
        this.chapters = chapters;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public CourseCategory getCourseCategory() {
        return courseCategory;
    }

    public void setCourseCategory(CourseCategory courseCategory) {
        this.courseCategory = courseCategory;
    }

    public Set<CourseTag> getCourseTags() {
        return courseTags;
    }

    public void setCourseTags(Set<CourseTag> courseTags) {
        this.courseTags = courseTags;
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

    /**
     * * 获取搜索关键词
	 *
     * @return 搜索关键词
	 */
    public String getKeyword() {
        return keyword;
    }

    /**
     * 设置搜索关键词
     *
     * @param keyword
     *            搜索关键词
     */
    public void setKeyword(String keyword) {
        if (keyword != null) {
            keyword = keyword.replaceAll("[,\\s]*,[,\\s]*", ",").replaceAll("^,|,$", "");
        }
        this.keyword = keyword;
    }

    /**
     * 获取评分
     *
     * @return 评分
     */
    public Float getScore() {
        return score;
    }

    /**
     * 设置评分
     *
     * @param score
     *            评分
     */
    public void setScore(Float score) {
        this.score = score;
    }

    /**
     * 获取总评分
     *
     * @return 总评分
     */
    public Long getTotalScore() {
        return totalScore;
    }

    /**
     * 设置总评分
     *
     * @param totalScore
     *            总评分
     */
    public void setTotalScore(Long totalScore) {
        this.totalScore = totalScore;
    }

    /**
     * 获取评分数
     *
     * @return 评分数
     */
    public Long getScoreCount() {
        return scoreCount;
    }

    /**
     * 设置评分数
     *
     * @param scoreCount
     *            评分数
     */
    public void setScoreCount(Long scoreCount) {
        this.scoreCount = scoreCount;
    }

    /**
     * 获取周点击数
     *
     * @return 周点击数
     */
    public Long getWeekHits() {
        return weekHits;
    }

    /**
     * 设置周点击数
     *
     * @param weekHits
     *            周点击数
     */
    public void setWeekHits(Long weekHits) {
        this.weekHits = weekHits;
    }

    /**
     * 获取月点击数
     *
     * @return 月点击数
     */
    public Long getMonthHits() {
        return monthHits;
    }

    /**
     * 设置月点击数
     *
     * @param monthHits
     *            月点击数
     */
    public void setMonthHits(Long monthHits) {
        this.monthHits = monthHits;
    }

    /**
     * 获取点击数
     *
     * @return 点击数
     */
    public Long getHits() {
        return hits;
    }

    /**
     * 设置点击数
     *
     * @param hits
     *            点击数
     */
    public void setHits(Long hits) {
        this.hits = hits;
    }

    /**
     * 获取周销量
     *
     * @return 周销量
     */
    public Long getWeekSales() {
        return weekSales;
    }

    /**
     * 设置周销量
     *
     * @param weekSales
     *            周销量
     */
    public void setWeekSales(Long weekSales) {
        this.weekSales = weekSales;
    }

    /**
     * 获取月销量
     *
     * @return 月销量
     */
    public Long getMonthSales() {
        return monthSales;
    }

    /**
     * 设置月销量
     *
     * @param monthSales
     *            月销量
     */
    public void setMonthSales(Long monthSales) {
        this.monthSales = monthSales;
    }

    /**
     * 获取销量
     *
     * @return 销量
     */
    public Long getSales() {
        return sales;
    }

    /**
     * 设置销量
     *
     * @param sales
     *            销量
     */
    public void setSales(Long sales) {
        this.sales = sales;
    }

    /**
     * 获取周点击数更新日期
     *
     * @return 周点击数更新日期
     */
    public Date getWeekHitsDate() {
        return weekHitsDate;
    }

    /**
     * 设置周点击数更新日期
     *
     * @param weekHitsDate
     *            周点击数更新日期
     */
    public void setWeekHitsDate(Date weekHitsDate) {
        this.weekHitsDate = weekHitsDate;
    }

    /**
     * 获取月点击数更新日期
     *
     * @return 月点击数更新日期
     */
    public Date getMonthHitsDate() {
        return monthHitsDate;
    }

    /**
     * 设置月点击数更新日期
     *
     * @param monthHitsDate
     *            月点击数更新日期
     */
    public void setMonthHitsDate(Date monthHitsDate) {
        this.monthHitsDate = monthHitsDate;
    }

    /**
     * 获取周销量更新日期
     *
     * @return 周销量更新日期
     */
    public Date getWeekSalesDate() {
        return weekSalesDate;
    }

    /**
     * 设置周销量更新日期
     *
     * @param weekSalesDate
     *            周销量更新日期
     */
    public void setWeekSalesDate(Date weekSalesDate) {
        this.weekSalesDate = weekSalesDate;
    }

    /**
     * 获取月销量更新日期
     *
     * @return 月销量更新日期
     */
    public Date getMonthSalesDate() {
        return monthSalesDate;
    }

    /**
     * 设置月销量更新日期
     *
     * @param monthSalesDate
     *            月销量更新日期
     */
    public void setMonthSalesDate(Date monthSalesDate) {
        this.monthSalesDate = monthSalesDate;
    }

    /**
     * 是否上架
     */
    @Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
    @NotNull
    @Column(nullable = false)
    private Boolean isMarketable;

    /**
     * 是否列出
     */
    @Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
    @NotNull
    @Column(nullable = false)
    private Boolean isList;

    /**
     * 是否置顶
     */
    @Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
    @NotNull
    @Column(nullable = false)
    private Boolean isTop;

    public Boolean getIsMarketable() {
        return isMarketable;
    }

    public void setIsMarketable(Boolean isMarketable) {
        this.isMarketable = isMarketable;
    }

    public Boolean getIsList() {
        return isList;
    }

    public void setIsList(Boolean isList) {
        this.isList = isList;
    }

    public Boolean getIsTop() {
        return isTop;
    }

    public void setIsTop(Boolean isTop) {
        this.isTop = isTop;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    /**
     * 是否有效
     */
    @Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
    @Column(nullable = false)
    private Boolean isActive;

    @Transient
    @JsonView({ListView.class})
    public String getTeacherName(){
        if(teacher!=null){
            return teacher.getName();
        }
        return null;
    }

    @Transient
    @JsonView({EditView.class})
    public Long getTeacherId(){
        if(teacher!=null){
            return teacher.getId();
        }
        return null;
    }

    @Transient
    @JsonView({ListView.class})
    public String getCourseCategoryName(){
        if(courseCategory!=null){
            return courseCategory.getName();
        }
        return null;
    }

    @Transient
    @JsonView({EditView.class})
    public Long[] getCourseCategoryIds(){
        if(courseCategory!=null){
            return ArrayUtils.add(courseCategory.getParentIds(),courseCategory.getId());
        }
        return null;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void init(){
        setChapters(new HashSet<>());
        setCourseCategory(null);
        setCourseComments(new HashSet<>());
        setCourseConsultations(new HashSet<>());
        setCourseTags(new HashSet<>());
        setDescription(null);
        setHits(0L);
        setImage(null);
        setIsActive(true);
        setIsList(true);
        setIsMarketable(true);
        setIsTop(true);
        setKeyword(null);
        setMemo(null);
        setMonthHits(0L);
        setMonthHitsDate(new Date());
        setMonthSales(0L);
        setMonthSalesDate(new Date());
        setOrder(0);
        setParts(new HashSet<>());
        setPrice(BigDecimal.ZERO);
        setSales(0L);
        setScore(5f);
        setScoreCount(0L);
        setSn(null);
        setTeacher(null);
        setTitle(null);
        setTotalScore(0L);
        setWeekHits(0L);
        setWeekHitsDate(new Date());
        setWeekSales(0L);
        setWeekSalesDate(new Date());
    }


    public interface ListView extends BaseView{}
    public interface EditView extends IdView{}
    public interface AllListView extends IdView{}
    public interface WebView extends IdView{}
}
