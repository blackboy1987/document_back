
package com.igomall.controller.admin.setting;

import com.igomall.controller.admin.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.igomall.common.Message;
import com.igomall.common.Pageable;
import com.igomall.entity.setting.FriendLink;
import com.igomall.service.setting.FriendLinkService;

/**
 * Controller - 友情链接
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@RestController("adminFriendLinkController")
@RequestMapping("/admin/api/friend_link")
public class FriendLinkController extends BaseController {

	@Autowired
	private FriendLinkService friendLinkService;

	/**
	 * 添加
	 */
	@GetMapping("/add")
	public String add(ModelMap model) {
		model.addAttribute("types", FriendLink.Type.values());
		return "admin/friend_link/add";
	}

	/**
	 * 保存
	 */
	@PostMapping("/save")
	public String save(FriendLink friendLink, RedirectAttributes redirectAttributes) {
		if (!isValid(friendLink)) {
			return ERROR_VIEW;
		}
		if (FriendLink.Type.text.equals(friendLink.getType())) {
			friendLink.setLogo(null);
		} else if (StringUtils.isEmpty(friendLink.getLogo())) {
			return ERROR_VIEW;
		}
		friendLinkService.save(friendLink);
		return "redirect:list";
	}

	/**
	 * 编辑
	 */
	@GetMapping("/edit")
	public String edit(Long id, ModelMap model) {
		model.addAttribute("types", FriendLink.Type.values());
		model.addAttribute("friendLink", friendLinkService.find(id));
		return "admin/friend_link/edit";
	}

	/**
	 * 更新
	 */
	@PostMapping("/update")
	public String update(FriendLink friendLink, RedirectAttributes redirectAttributes) {
		if (!isValid(friendLink)) {
			return ERROR_VIEW;
		}
		if (FriendLink.Type.text.equals(friendLink.getType())) {
			friendLink.setLogo(null);
		} else if (StringUtils.isEmpty(friendLink.getLogo())) {
			return ERROR_VIEW;
		}
		friendLinkService.update(friendLink);
		return "redirect:list";
	}

	/**
	 * 列表
	 */
	@GetMapping("/list")
	public String list(Pageable pageable, ModelMap model) {
		model.addAttribute("page", friendLinkService.findPage(pageable));
		return "admin/friend_link/list";
	}

	/**
	 * 删除
	 */
	@PostMapping("/delete")
	public @ResponseBody Message delete(Long[] ids) {
		friendLinkService.delete(ids);
		return SUCCESS_MESSAGE;
	}

}