package com.igomall.controller.member;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.entity.BaseEntity;
import com.igomall.entity.document.Resource;
import com.igomall.entity.document.ResourceCategory;
import com.igomall.entity.document.ResourceTag;
import com.igomall.service.document.ResourceCategoryService;
import com.igomall.service.document.ResourceService;
import com.igomall.service.document.ResourceTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController("apiToolController")
@RequestMapping("/member/api/tool")
public class ToolController extends BaseController {
    @Autowired
    private ResourceCategoryService resourceCategoryService;

    @Autowired
    private ResourceService resourceService;

    @PostMapping("/index")
    @JsonView(BaseEntity.ApiListView.class)
    public List<ResourceCategory> index(Long id){
        List<ResourceCategory> resourceCategories = new ArrayList<>();
        ResourceCategory root = resourceCategoryService.find(id);
        for (ResourceCategory parent:root.getChildren()) {
            resourceCategories.addAll(parent.getChildren());
        }
        for (ResourceCategory resourceCategory:resourceCategories) {
            for (Resource resource:resourceCategory.getResources()) {
                resource.setDownloadHits(resourceService.downloadHits(resource.getId()));
            }
        }


        return resourceCategories;
    }
}
