
package com.igomall.controller.admin.setting;

import com.igomall.controller.admin.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.igomall.common.Message;
import com.igomall.common.Pageable;
import com.igomall.entity.setting.ArticleTag;
import com.igomall.entity.BaseEntity;
import com.igomall.service.setting.ArticleTagService;

/**
 * Controller - 文章标签
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@RestController("adminArticleTagController")
@RequestMapping("/admin/api/article_tag")
public class ArticleTagController extends BaseController {

	@Autowired
	private ArticleTagService articleTagService;

	/**
	 * 添加
	 */
	@GetMapping("/add")
	public String add(ModelMap model) {
		return "admin/article_tag/add";
	}

	/**
	 * 保存
	 */
	@PostMapping("/save")
	public String save(ArticleTag articleTag, RedirectAttributes redirectAttributes) {
		if (!isValid(articleTag, BaseEntity.Save.class)) {
			return ERROR_VIEW;
		}
		articleTag.setArticles(null);
		articleTagService.save(articleTag);
		return "redirect:list";
	}

	/**
	 * 编辑
	 */
	@GetMapping("/edit")
	public String edit(Long id, ModelMap model) {
		model.addAttribute("articleTag", articleTagService.find(id));
		return "admin/article_tag/edit";
	}

	/**
	 * 更新
	 */
	@PostMapping("/update")
	public String update(ArticleTag articleTag, RedirectAttributes redirectAttributes) {
		if (!isValid(articleTag)) {
			return ERROR_VIEW;
		}
		articleTagService.update(articleTag, "articles");
		return "redirect:list";
	}

	/**
	 * 列表
	 */
	@GetMapping("/list")
	public String list(Pageable pageable, ModelMap model) {
		model.addAttribute("page", articleTagService.findPage(pageable));
		return "admin/article_tag/list";
	}

	/**
	 * 删除
	 */
	@PostMapping("/delete")
	public @ResponseBody Message delete(Long[] ids) {
		articleTagService.delete(ids);
		return SUCCESS_MESSAGE;
	}

}