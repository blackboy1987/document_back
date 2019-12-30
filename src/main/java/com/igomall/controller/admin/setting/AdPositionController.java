
package com.igomall.controller.admin.setting;

import com.igomall.common.Message;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.controller.admin.BaseController;
import com.igomall.entity.setting.AdPosition;
import com.igomall.service.setting.AdPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller - 广告位
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@Controller("adminAdPositionController")
@RequestMapping("/admin/api/ad_position")
public class AdPositionController extends BaseController {

	@Autowired
	private AdPositionService adPositionService;

	/**
	 * 保存
	 */
	@PostMapping("/save")
	public Message save(AdPosition adPosition) {
		if (!isValid(adPosition)) {
			return Message.success("参数错误");
		}
		adPosition.setAds(null);
		adPositionService.save(adPosition);
		return Message.success("操作成功");
	}

	/**
	 * 编辑
	 */
	@PostMapping("/edit")
	public AdPosition edit(Long id) {
		return adPositionService.find(id);
	}

	/**
	 * 更新
	 */
	@PostMapping("/update")
	public Message update(AdPosition adPosition) {
		if (!isValid(adPosition)) {
			return Message.success("参数错误");
		}
		adPositionService.update(adPosition, "ads");
		return Message.success("操作成功");
	}

	/**
	 * 列表
	 */
	@PostMapping("/list")
	public Page <AdPosition> list(Pageable pageable) {
		return adPositionService.findPage(pageable);
	}

	/**
	 * 删除
	 */
	@PostMapping("/delete")
	public Message delete(Long[] ids) {
		adPositionService.delete(ids);
		return SUCCESS_MESSAGE;
	}

}