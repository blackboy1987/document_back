package com.igomall.controller.member;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.entity.BaseEntity;
import com.igomall.entity.Resource;
import com.igomall.entity.ResourceTag;
import com.igomall.service.ResourceService;
import com.igomall.service.ResourceTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController("apiResourceController")
@RequestMapping("/api/resource")
public class ResourceController extends BaseController {

    @Autowired
    private ResourceService resourceService;
    @Autowired
    private ResourceTagService resourceTagService;

    @PostMapping("/index")
    @JsonView(BaseEntity.ApiListView.class)
    public List<ResourceTag> index(){
        return resourceTagService.findAll();
    }

    @PostMapping("/download")
    public List<String> download(Long id){
        Resource resource = resourceService.find(id);
        List<String> result = new ArrayList<>();
        if(resource!=null){
            List<String> resUrls = resourceService.find(id).getResUrls();
            if(resUrls!=null && resUrls.size()>0){
                result.add(resUrls.get(0));
            }
        }
        return result;
    }
}
