
package com.igomall.controller.admin.setting;

import com.igomall.controller.admin.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.igomall.common.Message;
import com.igomall.common.Pageable;
import com.igomall.entity.setting.Ad;
import com.igomall.service.setting.AdPositionService;
import com.igomall.service.setting.AdService;

/**
 * Controller - 广告
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@RestController("adminAdController")
@RequestMapping("/admin/api/ad")
public class AdController extends BaseController {

	@Autowired
	private AdService adService;
	@Autowired
	private AdPositionService adPositionService;

	/**
	 * 添加
	 */
	@GetMapping("/add")
	public String add(ModelMap model) {
		model.addAttribute("types", Ad.Type.values());
		model.addAttribute("adPositions", adPositionService.findAll());
		return "admin/ad/add";
	}

	/**
	 * 保存
	 */
	@PostMapping("/save")
	public String save(Ad ad, Long adPositionId, RedirectAttributes redirectAttributes) {
		ad.setAdPosition(adPositionService.find(adPositionId));
		if (!isValid(ad)) {
			return ERROR_VIEW;
		}
		if (ad.getBeginDate() != null && ad.getEndDate() != null && ad.getBeginDate().after(ad.getEndDate())) {
			return ERROR_VIEW;
		}
		if (Ad.Type.text.equals(ad.getType())) {
			ad.setPath(null);
		} else {
			ad.setContent(null);
		}
		adService.save(ad);
		return "redirect:list";
	}

	/**
	 * 编辑
	 */
	@GetMapping("/edit")
	public String edit(Long id, ModelMap model) {
		model.addAttribute("types", Ad.Type.values());
		model.addAttribute("ad", adService.find(id));
		model.addAttribute("adPositions", adPositionService.findAll());
		return "admin/ad/edit";
	}

	/**
	 * 更新
	 */
	@PostMapping("/update")
	public String update(Ad ad, Long adPositionId, RedirectAttributes redirectAttributes) {
		ad.setAdPosition(adPositionService.find(adPositionId));
		if (!isValid(ad)) {
			return ERROR_VIEW;
		}
		if (ad.getBeginDate() != null && ad.getEndDate() != null && ad.getBeginDate().after(ad.getEndDate())) {
			return ERROR_VIEW;
		}
		if (Ad.Type.text.equals(ad.getType())) {
			ad.setPath(null);
		} else {
			ad.setContent(null);
		}
		adService.update(ad);
		return "redirect:list";
	}

	/**
	 * 列表
	 */
	@GetMapping("/list")
	public String list(Pageable pageable, ModelMap model) {
		model.addAttribute("page", adService.findPage(pageable));
		return "admin/ad/list";
	}

	/**
	 * 删除
	 */
	@PostMapping("/delete")
	public @ResponseBody Message delete(Long[] ids) {
		adService.delete(ids);
		return SUCCESS_MESSAGE;
	}

}