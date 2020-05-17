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
@Table(name = "edu_tool_category")
public class ToolCategory extends OrderedEntity<Long> {

    /**
     * 树路径分隔符
     */
    public static final String TREE_PATH_SEPARATOR = ",";

    @NotEmpty
    @Column(nullable = false)
    @JsonView({JsonApiView.class,ListView.class,EditView.class,ApiListView.class})
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
    private ToolCategory parent;

    /**
     * 下级分类
     */
    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    @OrderBy("order asc")
    @JsonView({JsonApiView.class})
    private Set<ToolCategory> children = new HashSet<>();

    @OneToMany(mappedBy = "toolCategory",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JsonView({JsonApiView.class})
    private Set<ToolItem> toolItems = new HashSet<>();

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

    public ToolCategory getParent() {
        return parent;
    }

    public void setParent(ToolCategory parent) {
        this.parent = parent;
    }

    public Set<ToolCategory> getChildren() {
        return children;
    }

    public void setChildren(Set<ToolCategory> children) {
        this.children = children;
    }

    public Set<ToolItem> getToolItems() {
        return toolItems;
    }

    public void setToolItems(Set<ToolItem> toolItems) {
        this.toolItems = toolItems;
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
    public List<ToolCategory> getParents() {
        List<ToolCategory> parents = new ArrayList<>();
        recursiveParents(parents, this);
        return parents;
    }

    /**
     * 递归上级分类
     *
     * @param parents
     *            上级分类
     * @param toolCategory
     *            文章分类
     */
    private void recursiveParents(List<ToolCategory> parents, ToolCategory toolCategory) {
        if (toolCategory == null) {
            return;
        }
        ToolCategory parent = toolCategory.getParent();
        if (parent != null) {
            parents.add(0, parent);
            recursiveParents(parents, parent);
        }
    }



    @Transient
    @JsonView({ApiListView.class})
    public Set<ToolItem> getItems(){
        return toolItems;
    }
}
