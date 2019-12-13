
package com.igomall.plugin.localStorage;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.igomall.controller.admin.BaseController;
import com.igomall.entity.PluginConfig;
import com.igomall.service.PluginConfigService;

/**
 * Controller - 本地文件存储
 * 
 * @author blackboy
 * @version 1.0
 */
@RestController("adminLocalStorageController")
@RequestMapping("/admin/storage_plugin/local_storage")
public class LocalStorageController extends BaseController {

	@Autowired
	private LocalStoragePlugin localStoragePlugin;
	@Autowired
	private PluginConfigService pluginConfigService;

	/**
	 * 设置
	 */
	@GetMapping("/setting")
	public String setting(ModelMap model) {
		PluginConfig pluginConfig = localStoragePlugin.getPluginConfig();
		model.addAttribute("pluginConfig", pluginConfig);
		return "/com/igomall/plugin/localStorage/setting";
	}

	/**
	 * 更新
	 */
	@PostMapping("/update")
	public String update(Integer order, RedirectAttributes redirectAttributes) {
		PluginConfig pluginConfig = localStoragePlugin.getPluginConfig();
		pluginConfig.setIsEnabled(true);
		pluginConfig.setOrder(order);
		pluginConfigService.update(pluginConfig);
		return "redirect:/admin/storage_plugin/list";
	}

}