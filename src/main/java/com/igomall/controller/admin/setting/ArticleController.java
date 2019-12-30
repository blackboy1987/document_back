
package com.igomall.controller.admin.setting;

import com.igomall.common.Message;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.controller.admin.BaseController;
import com.igomall.entity.setting.Article;
import com.igomall.service.setting.ArticleCategoryService;
import com.igomall.service.setting.ArticleService;
import com.igomall.service.setting.ArticleTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;

/**
 * Controller - 文章
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@RestController("adminArticleController")
@RequestMapping("/admin/api/article")
public class ArticleController extends BaseController {

	@Autowired
	private ArticleService articleService;
	@Autowired
	private ArticleCategoryService articleCategoryService;
	@Autowired
	private ArticleTagService articleTagService;

	/**
	 * 添加
	 */
	@GetMapping("/add")
	public String add(ModelMap model) {
		model.addAttribute("articleCategoryTree", articleCategoryService.findTree());
		model.addAttribute("articleTags", articleTagService.findAll());
		return "admin/article/add";
	}

	/**
	 * 保存
	 */
	@PostMapping("/save")
	public Message save(Article article, Long articleCategoryId, Long[] articleTagIds ) {
		article.setArticleCategory(articleCategoryService.find(articleCategoryId));
		article.setArticleTags(new HashSet<>(articleTagService.findList(articleTagIds)));
		if (!isValid(article)) {
			return Message.success("参数错误");
		}
		article.setHits(0L);
		articleService.save(article);
		return Message.success("操作成功");
	}

	/**
	 * 编辑
	 */
	@PostMapping("/edit")
	public Article edit(Long id) {
		return articleService.find(id);
	}

	/**
	 * 更新
	 */
	@PostMapping("/update")
	public Message update(Article article, Long articleCategoryId, Long[] articleTagIds) {
		article.setArticleCategory(articleCategoryService.find(articleCategoryId));
		article.setArticleTags(new HashSet<>(articleTagService.findList(articleTagIds)));
		if (!isValid(article)) {
			return Message.error("参数错误");
		}
		articleService.update(article, "hits");
		return Message.success("操作成功");
	}

	/**
	 * 列表
	 */
	@PostMapping("/list")
	public Page<Article> list(Pageable pageable) {
		return articleService.findPage(pageable);
	}

	/**
	 * 删除
	 */
	@PostMapping("/delete")
	public Message delete(Long[] ids) {
		articleService.delete(ids);
		return SUCCESS_MESSAGE;
	}

}