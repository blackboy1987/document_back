
package com.igomall.controller.admin.setting;

import com.igomall.controller.admin.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.igomall.common.Message;
import com.igomall.common.Pageable;
import com.igomall.entity.setting.AdPosition;
import com.igomall.service.setting.AdPositionService;

/**
 * Controller - 广告位
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@Controller("adminAdPositionController")
@RequestMapping("/admin/ad_position")
public class AdPositionController extends BaseController {

	@Autowired
	private AdPositionService adPositionService;

	/**
	 * 添加
	 */
	@GetMapping("/add")
	public String add(ModelMap model) {
		return "admin/ad_position/add";
	}

	/**
	 * 保存
	 */
	@PostMapping("/save")
	public String save(AdPosition adPosition, RedirectAttributes redirectAttributes) {
		if (!isValid(adPosition)) {
			return ERROR_VIEW;
		}
		adPosition.setAds(null);
		adPositionService.save(adPosition);
		return "redirect:list";
	}

	/**
	 * 编辑
	 */
	@GetMapping("/edit")
	public String edit(Long id, ModelMap model) {
		model.addAttribute("adPosition", adPositionService.find(id));
		return "admin/ad_position/edit";
	}

	/**
	 * 更新
	 */
	@PostMapping("/update")
	public String update(AdPosition adPosition, RedirectAttributes redirectAttributes) {
		if (!isValid(adPosition)) {
			return ERROR_VIEW;
		}
		adPositionService.update(adPosition, "ads");
		return "redirect:list";
	}

	/**
	 * 列表
	 */
	@GetMapping("/list")
	public String list(Pageable pageable, ModelMap model) {
		model.addAttribute("page", adPositionService.findPage(pageable));
		return "admin/ad_position/list";
	}

	/**
	 * 删除
	 */
	@PostMapping("/delete")
	public @ResponseBody Message delete(Long[] ids) {
		adPositionService.delete(ids);
		return SUCCESS_MESSAGE;
	}

}