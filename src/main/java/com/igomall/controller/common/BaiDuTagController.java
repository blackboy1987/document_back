
package com.igomall.controller.common;

import com.igomall.entity.wechat.BaiDuTag;
import com.igomall.service.wechat.BaiDuTagService;
import com.igomall.util.CodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller - 文章
 * 
 * @author blackboy
 * @version 1.0
 */
@RestController("shopBaiDuTagController")
@RequestMapping("/baidu_tag")
public class BaiDuTagController extends BaseController {

	@Autowired
	private BaiDuTagService baiDuTagService;

	/**
	 * 点击数
	 */
	@GetMapping("create")
	public String create(BaiDuTag baiDuTag) {
		baiDuTag.setCode(CodeUtils.getNumberCode(3));
		while (baiDuTagService.codeExist(baiDuTag.getCode())){
			baiDuTag.setCode(CodeUtils.getNumberCode(3));
		}
		baiDuTagService.save(baiDuTag);
		return "ok";
	}

}