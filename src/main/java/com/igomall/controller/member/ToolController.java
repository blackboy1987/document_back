package com.igomall.controller.member;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.controller.common.BaseController;
import com.igomall.entity.BaseEntity;
import com.igomall.entity.wechat.ToolCategory;
import com.igomall.entity.wechat.ToolItem;
import com.igomall.service.wechat.ToolCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController("commonToolController")
@RequestMapping("/member/api/tool")
public class ToolController extends BaseController {

    @Autowired
    private ToolCategoryService toolCategoryService;

    @PostMapping("/index")
    @JsonView(BaseEntity.ApiListView.class)
    public List<ToolCategory> project(){
        List<ToolCategory> toolCategories = toolCategoryService.findRoots();
        for (ToolCategory toolCategory:toolCategories) {
            Set<ToolItem> toolItems =  new HashSet<>();
            for (ToolCategory child:toolCategory.getChildren()) {
                toolItems.addAll(child.getToolItems());
            }
            toolCategory.setToolItems(toolItems);
        }
        return toolCategories;
    }
}
