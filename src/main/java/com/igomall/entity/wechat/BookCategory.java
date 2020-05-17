package com.igomall.entity.wechat;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.entity.OrderedEntity;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "edu_book_category")
public class BookCategory extends OrderedEntity<Long> {

    /**
     * 树路径分隔符
     */
    public static final String TREE_PATH_SEPARATOR = ",";

    @NotEmpty
    @Column(nullable = false)
    @JsonView({JsonApiView.class,ListView.class,EditView.class})
    private String name;

    /**
     * 树路径
     */
    @Column(nullable = false)
    private String treePath;

    /**
     * 层级
     */
    @Column(nullable = false)
    @JsonView({ListView.class})
    private Integer grade;

    /**
     * 上级分类
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private BookCategory parent;

    /**
     * 下级分类
     */
    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    @OrderBy("order asc")
    @JsonView({JsonApiView.class})
    private Set<BookCategory> children = new HashSet<>();

    @OneToMany(mappedBy = "bookCategory",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Set<BookItem> bookItems = new HashSet<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTreePath() {
        return treePath;
    }

    public void setTreePath(String treePath) {
        this.treePath = treePath;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public BookCategory getParent() {
        return parent;
    }

    public void setParent(BookCategory parent) {
        this.parent = parent;
    }

    public Set<BookCategory> getChildren() {
        return children;
    }

    public void setChildren(Set<BookCategory> children) {
        this.children = children;
    }

    public Set<BookItem> getBookItems() {
        return bookItems;
    }

    public void setBookItems(Set<BookItem> bookItems) {
        this.bookItems = bookItems;
    }


    /**
     * 获取所有上级分类ID
     *
     * @return 所有上级分类ID
     */
    @Transient
    public Long[] getParentIds() {
        String[] parentIds = StringUtils.split(getTreePath(), TREE_PATH_SEPARATOR);
        Long[] result = new Long[parentIds.length];
        for (int i = 0; i < parentIds.length; i++) {
            result[i] = Long.valueOf(parentIds[i]);
        }
        return result;
    }

    @Transient
    @JsonView({EditView.class})
    public Long getParentId() {
        if(parent!=null){
            return parent.getId();
        }
        return null;
    }

    /**
     * 获取所有上级分类
     *
     * @return 所有上级分类
     */
    @Transient
    public List<BookCategory> getParents() {
        List<BookCategory> parents = new ArrayList<>();
        recursiveParents(parents, this);
        return parents;
    }

    /**
     * 递归上级分类
     *
     * @param parents
     *            上级分类
     * @param bookCategory
     *            文章分类
     */
    private void recursiveParents(List<BookCategory> parents, BookCategory bookCategory) {
        if (bookCategory == null) {
            return;
        }
        BookCategory parent = bookCategory.getParent();
        if (parent != null) {
            parents.add(0, parent);
            recursiveParents(parents, parent);
        }
    }

    @Transient
    @JsonView({ApiListView.class})
    public Set<BookItem> getItems(){
        return bookItems;
    }

}
