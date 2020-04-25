package com.igomall.controller.admin;

import com.igomall.entity.Resource;
import com.igomall.service.ResourceService;
import com.igomall.util.HanyuPinyinUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/resource")
public class ResourceController extends BaseController {

    @Autowired
    private ResourceService resourceService;

    @GetMapping("/index")
    public String index(){
        String path = "F:\\BaiduNetdiskDownload\\新建文件夹\\web前端教程\\【05】开发工具+图书+参考手册\\图书+手册\\100本最棒前端开发图书";
        String path1 = "F:\\BaiduNetdiskDownload\\新建文件夹\\web前端教程\\【05】开发工具+图书+参考手册\\图书+手册\\files";
        File parent = new File(path);
        File[] files = parent.listFiles();

        for (File file:files) {
            String resourceName = HanyuPinyinUtils.pinyin(file.getName()).toLowerCase();

            Resource resource1 = resourceService.findByName(resourceName);
            if(resource1!=null){
                continue;
            }

            File file1 = new File(path1,resourceName.toLowerCase());
            try{
                FileUtils.copyFile(file,file1);
                List<String> resUrls = new ArrayList<>();
                resUrls.add("http://res.i-gomall.com/"+resourceName);
                resUrls.add("https://file.i-gomall.com/file/"+resourceName);
                Resource resource = new Resource();
                resource.setName(file.getName());
                resource.setResUrls(resUrls);
                resourceService.save(resource);
                System.out.println("=====================ok");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return "ok";
    }
}
