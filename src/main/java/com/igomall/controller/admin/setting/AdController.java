
package com.igomall.controller.admin.setting;

import com.igomall.common.Message;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.controller.admin.BaseController;
import com.igomall.entity.setting.Ad;
import com.igomall.service.setting.AdPositionService;
import com.igomall.service.setting.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	 * 保存
	 */
	@PostMapping("/save")
	public Message save(Ad ad, Long adPositionId) {
		ad.setAdPosition(adPositionService.find(adPositionId));
		if (!isValid(ad)) {
			return Message.error("参数错误");
		}
		if (ad.getBeginDate() != null && ad.getEndDate() != null && ad.getBeginDate().after(ad.getEndDate())) {
			return Message.error("时间设置错误");
		}
		if (Ad.Type.text.equals(ad.getType())) {
			ad.setPath(null);
		} else {
			ad.setContent(null);
		}
		adService.save(ad);
		return Message.success("操作成功");
	}

	/**
	 * 编辑
	 */
	@PostMapping("/edit")
	public Ad edit(Long id) {
		return adService.find(id);
	}

	/**
	 * 更新
	 */
	@PostMapping("/update")
	public Message update(Ad ad, Long adPositionId) {
		ad.setAdPosition(adPositionService.find(adPositionId));
		if (!isValid(ad)) {
			return Message.error("参数错误");
		}
		if (ad.getBeginDate() != null && ad.getEndDate() != null && ad.getBeginDate().after(ad.getEndDate())) {
			return Message.error("时间设置错误");
		}
		if (Ad.Type.text.equals(ad.getType())) {
			ad.setPath(null);
		} else {
			ad.setContent(null);
		}
		adService.update(ad);
		return Message.success("操作成功");
	}

	/**
	 * 列表
	 */
	@PostMapping("/list")
	public Page<Ad> list(Pageable pageable) {
		return adService.findPage(pageable);
	}

	/**
	 * 删除
	 */
	@PostMapping("/delete")
	public Message delete(Long[] ids) {
		adService.delete(ids);
		return SUCCESS_MESSAGE;
	}

}