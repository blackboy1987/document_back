
package com.igomall.controller.admin;


import com.igomall.common.Message;
import com.igomall.common.Pageable;
import com.igomall.entity.BaseEntity;
import com.igomall.entity.Resource;
import com.igomall.entity.ResourceTag;
import com.igomall.service.ResourceService;
import com.igomall.service.ResourceTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller - 商品标签
 *
 * @author blackboy
 * @version 1.0
 */
@RestController("adminResourceTagController")
@RequestMapping("/api/resource_tag")
public class ResourceTagController extends BaseController {

	@Autowired
	private ResourceTagService resourceTagService;
	@Autowired
	private ResourceService resourceService;

	/**
	 *
	 * @return
	 */
	@GetMapping("/init")
	public String init(){
		List<Resource> resources = resourceService.findAll();
		List<ResourceTag> resourceTags = resourceTagService.findAll();
		for (Resource resource:resources) {
			for (ResourceTag resourceTag:resourceTags) {
				if(resource.getName().toLowerCase().contains(resourceTag.getName().toLowerCase())){
					resource.getResourceTags().add(resourceTag);
				}
			}
			resourceService.update(resource);
		}
    ResourceTag resourceTag = resourceTagService.find(175L);
    for (Resource resource:resources) {
      if(resource.getResourceTags().isEmpty()){
        resource.getResourceTags().add(resourceTag);
      }
      resourceService.update(resource);
    }

		return "ok";
	}

	private void save(List<String> tags){
		for (String tag:tags) {
			ResourceTag resourceTag = resourceTagService.findByName(tag);
			if(resourceTag==null){
				resourceTag = new ResourceTag();
				resourceTag.setName(tag);
				resourceTag.setResources(null);
				resourceTagService.save(resourceTag);
			}
		}
	}


	/**
	 * 保存
	 */
	@PostMapping("/save")
	public String save(ResourceTag resourceTag) {
		if (!isValid(resourceTag, BaseEntity.Save.class)) {
			return "error";
		}
    ResourceTag resourceTag1 = resourceTagService.findByName(resourceTag.getName());
    if(resourceTag1==null){
      resourceTag1 = new ResourceTag();
      resourceTag1.setName(resourceTag.getName());
      resourceTag1.setResources(null);
      resourceTagService.save(resourceTag1);
      save1(resourceTag1);
    }
		return "redirect:list";
	}

	private void save1(ResourceTag resourceTag){
    List<Resource> resources = resourceService.findAll();
    for (Resource resource:resources) {
      if(resource.getName().toLowerCase().contains(resourceTag.getName().toLowerCase())){
        resource.getResourceTags().add(resourceTag);
      }
      resourceService.update(resource);
    }
  }

	/**
	 * 编辑
	 */
	@GetMapping("/edit")
	public String edit(Long id, ModelMap model) {
		model.addAttribute("resourceTag", resourceTagService.find(id));
		return "admin/resource_tag/edit";
	}

	/**
	 * 更新
	 */
	@PostMapping("/update")
	public String update(ResourceTag resourceTag) {
		if (!isValid(resourceTag)) {
			return "error";
		}
		resourceTagService.update(resourceTag, "resources");
		return "redirect:list";
	}

	/**
	 * 列表
	 */
	@GetMapping("/list")
	public String list(Pageable pageable, ModelMap model) {
		model.addAttribute("page", resourceTagService.findPage(pageable));
		return "admin/resource_tag/list";
	}

	/**
	 * 删除
	 */
	@PostMapping("/delete")
	public @ResponseBody
    Message delete(Long[] ids) {
		resourceTagService.delete(ids);
		return Message.success("ok");
	}

}
