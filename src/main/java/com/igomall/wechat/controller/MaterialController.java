package com.igomall.wechat.controller;

import com.igomall.common.Pageable;
import com.igomall.wechat.entity.material.NewsMaterialResponse;
import com.igomall.wechat.service.material.NewsMaterialService;
import com.igomall.wechat.util.MaterialUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("wechatMaterialController")
@RequestMapping("/wx/material")
public class MaterialController {

    @Autowired
    private NewsMaterialService newsMaterialService;

    @GetMapping("/news")
    public String index(Pageable pageable){
        NewsMaterialResponse newsMaterialResponse = MaterialUtils.getMaterial("news",pageable);
        List<NewsMaterialResponse.Material> newsMaterials = newsMaterialResponse.getMaterials();
        /*for (NewsMaterial newsMaterial:newsMaterials) {
            if(!newsMaterialService.mediaIdExists(newsMaterial.getMediaId())){
                newsMaterialService.save(newsMaterial);
            }
        }*/

        return "ok";
    }
}
