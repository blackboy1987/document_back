package com.igomall.controller.member;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.controller.common.BaseController;
import com.igomall.entity.BaseEntity;
import com.igomall.entity.member.Member;
import com.igomall.entity.wechat.ProjectItem;
import com.igomall.entity.wechat.ProjectCategory;
import com.igomall.entity.wechat.ProjectItem;
import com.igomall.security.CurrentUser;
import com.igomall.service.wechat.ProjectCategoryService;
import com.igomall.service.wechat.ProjectItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController("commonProjectController")
@RequestMapping("/member/api/project")
public class ProjectController extends BaseController {

    @Autowired
    private ProjectCategoryService projectCategoryService;

    @PostMapping("/index")
    @JsonView(BaseEntity.ApiListView.class)
    public List<ProjectCategory> project(){
        List<ProjectCategory> projectCategories = projectCategoryService.findRoots();
        for (ProjectCategory projectCategory:projectCategories) {
           Set<ProjectItem> projectItemSet =  new HashSet<>();
            for (ProjectCategory child:projectCategory.getChildren()) {
                projectItemSet.addAll(child.getProjectItems());
            }
            projectCategory.setProjectItems(projectItemSet);
        }
        return projectCategories;
    }
}
