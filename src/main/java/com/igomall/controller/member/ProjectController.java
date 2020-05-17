package com.igomall.controller.member;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.controller.common.BaseController;
import com.igomall.entity.BaseEntity;
import com.igomall.entity.Resource;
import com.igomall.entity.member.Member;
import com.igomall.entity.wechat.BookItem;
import com.igomall.entity.wechat.ProjectItem;
import com.igomall.entity.wechat.ProjectCategory;
import com.igomall.entity.wechat.ProjectItem;
import com.igomall.security.CurrentUser;
import com.igomall.service.wechat.ProjectCategoryService;
import com.igomall.service.wechat.ProjectItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController("commonProjectController")
@RequestMapping("/member/api/project")
public class ProjectController extends BaseController {

    @Autowired
    private ProjectCategoryService projectCategoryService;

    @Autowired
    private ProjectItemService projectItemService;

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



    @PostMapping("/download")
    public List<String> download(Long id){
        ProjectItem projectItem = projectItemService.find(id);
        List<String> result = new ArrayList<>();
        if(projectItem!=null&& StringUtils.isNoneEmpty(projectItem.getDownloadUrl())){
            result.add(projectItem.getDownloadUrl());
        }
        return result;
    }

    @PostMapping("/download_hits")
    public Map<String,Object> downloadHits(Long id){
        Map<String, Object> data = new HashMap<>();
        data.put("downloadHits", projectItemService.viewHits(id));
        return data;
    }
}
