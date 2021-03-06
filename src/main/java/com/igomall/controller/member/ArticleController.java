/*
 * Copyright 2005-2017 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package com.igomall.controller.member;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.common.Pageable;
import com.igomall.common.Results;
import com.igomall.entity.BaseEntity;
import com.igomall.entity.article.Article;
import com.igomall.entity.article.ArticleCategory;
import com.igomall.exception.ResourceNotFoundException;
import com.igomall.service.article.ArticleCategoryService;
import com.igomall.service.article.ArticleService;
import com.igomall.service.article.SearchService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller - 文章
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@Controller("shopArticleController")
@RequestMapping("/article")
public class ArticleController extends BaseController {

	/**
	 * 每页记录数
	 */
	private static final int PAGE_SIZE = 20;

	@Autowired
	private ArticleService articleService;
	@Autowired
	private ArticleCategoryService articleCategoryService;
	@Autowired
	private SearchService searchService;

	/**
	 * 详情
	 */
	@GetMapping("/detail/{articleId}")
	public String detail(@PathVariable Long articleId, @PathVariable Integer pageNumber, ModelMap model) {
		Article article = articleService.find(articleId);
		if (article == null || pageNumber < 1) {
			throw new ResourceNotFoundException();
		}
		model.addAttribute("article", article);
		model.addAttribute("pageNumber", pageNumber);
		return "shop/article/detail";
	}

	/**
	 * 列表
	 */
	@GetMapping("/list/{articleCategoryId}")
	public String list(@PathVariable Long articleCategoryId, Integer pageNumber, ModelMap model) {
		ArticleCategory articleCategory = articleCategoryService.find(articleCategoryId);
		if (articleCategory == null) {
			throw new ResourceNotFoundException();
		}
		Pageable pageable = new Pageable(pageNumber, PAGE_SIZE);
		model.addAttribute("articleCategory", articleCategory);
		model.addAttribute("page", articleService.findPage(articleCategory, null, true, pageable));
		return "shop/article/list";
	}

	/**
	 * 列表
	 */
	@GetMapping(path = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
	@JsonView(BaseEntity.BaseView.class)
	public ResponseEntity<?> list(Long articleCategoryId, Integer pageNumber) {
		ArticleCategory articleCategory = articleCategoryService.find(articleCategoryId);
		if (articleCategory == null) {
			return Results.NOT_FOUND;
		}

		Pageable pageable = new Pageable(pageNumber, PAGE_SIZE);
		return ResponseEntity.ok(articleService.findPage(articleCategory, null, true, pageable).getContent());
	}

	/**
	 * 搜索
	 */
	@GetMapping("/search")
	public String search(String keyword, Integer pageNumber, ModelMap model) {
		if (StringUtils.isEmpty(keyword)) {
			return UNPROCESSABLE_ENTITY_VIEW;
		}
		Pageable pageable = new Pageable(pageNumber, PAGE_SIZE);
		model.addAttribute("articleKeyword", keyword);
		model.addAttribute("page", searchService.search(keyword, pageable));
		return "shop/article/search";
	}

	/**
	 * 搜索
	 */
	@GetMapping(path = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
	@JsonView(BaseEntity.BaseView.class)
	public ResponseEntity<?> search(String keyword, Integer pageNumber) {
		if (StringUtils.isEmpty(keyword)) {
			return Results.UNPROCESSABLE_ENTITY;
		}
		Pageable pageable = new Pageable(pageNumber, PAGE_SIZE);
		return ResponseEntity.ok(searchService.search(keyword, pageable).getContent());
	}

	/**
	 * 点击数
	 */
	@GetMapping("/hits/{articleId}")
	public ResponseEntity<?> hits(@PathVariable Long articleId) {
		Map<String, Object> data = new HashMap<>();
		data.put("hits", articleService.viewHits(articleId));
		return ResponseEntity.ok(data);
	}

}