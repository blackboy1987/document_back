package com.igomall.controller.common;

import com.igomall.Demo;
import com.igomall.entity.document.Resource;
import com.igomall.entity.document.ResourceCategory;
import com.igomall.service.document.ResourceCategoryService;
import com.igomall.service.document.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;
import java.util.Set;

@RestController("index2Controller")
@RequestMapping("/index")
public class IndexController {

    @Autowired
    private ResourceCategoryService resourceCategoryService;

    @Autowired
    private ResourceService resourceService;
    @GetMapping("/init1")
    public String init1(){
        String [] aa = new String[]{
                "Java开发工具","Python开发工具","PHP开发工具","Go开发工具","大数据开发工具","H5前端开发工具","UI/UE设计工具","Linux运维工具"
        };
        create("开发工具",aa);

        String [] bb = new String[]{
                "Java开发手册大全","Python开发手册大全","PHP开发手册大全","Go开发手册大全","大数据开发手册大全","H5前端开发手册大全","UI设计手册大全","Linux运维手册大全"
        };
        create("开发手册",bb);

        String [] cc = new String[]{
                "Java精品项目源码","Python精品项目源码","PHP精品项目源码","Go精品项目源码","大数据精品项目源码","H5前端精品项目源码","UI产品原文件","运维解决方案"
        };
        create("项目源码",cc);



        return "ok";
    }

    @GetMapping("/init2")
    public String init2(){

        List<Resource> resources = resourceService.findAll();
        for (Resource resource:resources) {
            resource.setDownloadHits(resource.getDownloadHits()+new Random().nextInt(200));
            resourceService.update(resource);
        }



        return "ok";
    }


    private void create(String name,String[] aa){
        ResourceCategory resourceCategory1 = new ResourceCategory();
        resourceCategory1.setName(name);
        resourceCategory1.setTreePath(null);
        resourceCategory1.setGrade(null);
        resourceCategory1.setChildren(null);
        resourceCategory1.setResources(null);
        resourceCategory1 = resourceCategoryService.save(resourceCategory1);


        for (String a:aa) {
            ResourceCategory resourceCategory2 = new ResourceCategory();
            resourceCategory2.setName(a);
            resourceCategory2.setTreePath(null);
            resourceCategory2.setGrade(null);
            resourceCategory2.setChildren(null);
            resourceCategory2.setResources(null);
            resourceCategory2.setParent(resourceCategory1);
            resourceCategory2 = resourceCategoryService.save(resourceCategory2);
        }
    }


    @GetMapping
    public String init() throws Exception{
        List<ResourceCategory> result = Demo.main();
        for (ResourceCategory resourceCategory:result) {
            ResourceCategory resourceCategory1 = resourceCategoryService.findByName(resourceCategory.getName());
            if(resourceCategory1==null){
                String[] names = resourceCategory.getName().split("=");
                ResourceCategory parent = resourceCategoryService.findByName(names[0]);
                resourceCategory1 = new ResourceCategory();
                resourceCategory1.setParent(parent);
                resourceCategory1.setName(names[1]);
                resourceCategory1.setTreePath(null);
                resourceCategory1.setGrade(null);
                resourceCategory1.setChildren(null);
                resourceCategory1.setResources(null);
               resourceCategory1 = resourceCategoryService.save(resourceCategory1);
            }
           saveResource(resourceCategory1,resourceCategory.getResources());
        }
        return "ok";
    }

    private void saveResource(ResourceCategory resourceCategory, Set<Resource> resources){
        for (Resource resource:resources) {
            Resource resource1 = resourceService.findByName(resource.getName());

            if(resource1==null){
                resource1 = new Resource();
                resource1.setName(resource.getName());
                resource1.setMemo(resource.getMemo());
                resource1.setImg(resource.getImg());
                resource1.setDownloadHits(0L);
                resource1.setResUrls(resource.getResUrls());
                resource1.setResourceCategory(resourceCategory);
                resourceService.save(resource1);
            }else{
                resource1.setResourceCategory(resourceCategory);
                resourceService.update(resource1);
            }
        }
    }
}
