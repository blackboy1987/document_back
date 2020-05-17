package com.igomall.controller.member;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.controller.common.BaseController;
import com.igomall.entity.BaseEntity;
import com.igomall.entity.Resource;
import com.igomall.entity.wechat.ToolCategory;
import com.igomall.entity.wechat.ToolItem;
import com.igomall.service.wechat.ToolCategoryService;
import com.igomall.service.wechat.ToolItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController("commonToolController")
@RequestMapping("/member/api/tool")
public class ToolController extends BaseController {

    @Autowired
    private ToolCategoryService toolCategoryService;
    @Autowired
    private ToolItemService toolItemService;

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

    @PostMapping("/download")
    public List<String> download(Long id){
        ToolItem toolItem = toolItemService.find(id);
        List<String> result = new ArrayList<>();
        if(toolItem!=null&& StringUtils.isNoneEmpty(toolItem.getDownloadUrl())){
            result.add(toolItem.getDownloadUrl());
        }
        return result;
    }

    @PostMapping("/download_hits")
    public Map<String,Object> downloadHits(Long id){
        Map<String, Object> data = new HashMap<>();
        data.put("downloadHits", toolItemService.viewHits(id));
        return data;
    }
}
