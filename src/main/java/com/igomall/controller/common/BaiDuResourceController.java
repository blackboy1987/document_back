
package com.igomall.controller.common;

import com.igomall.entity.wechat.BaiDuResource;
import com.igomall.entity.wechat.BaiDuTag;
import com.igomall.service.wechat.BaiDuResourceService;
import com.igomall.service.wechat.BaiDuTagService;
import com.igomall.util.CodeUtils;
import com.igomall.util.HanLPUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.HashSet;
import java.util.List;

/**
 * Controller - 文章
 * 
 * @author blackboy
 * @version 1.0
 */
@RestController("shopBaiDuResourceController")
@RequestMapping("/baidu_resource")
public class BaiDuResourceController extends BaseController {

	@Autowired
	private BaiDuResourceService baiDuResourceService;
	@Autowired
	private BaiDuTagService baiDuTagService;

	/**
	 * 点击数
	 */
	@GetMapping("create")
	public String create(BaiDuResource baiDuResource) {
		baiDuResource.setCode(CodeUtils.getNumberCode(5));
		while (baiDuResourceService.codeExist(baiDuResource.getCode())){
			baiDuResource.setCode(CodeUtils.getNumberCode(5));
		}
		baiDuResource.setHits(0L);
		List<String> keywords = HanLPUtils.extractKeyword(baiDuResource.getTitle());
		for (String keyword:keywords) {
			BaiDuTag baiDuTag = baiDuTagService.findByName(keyword);
			if(baiDuTag==null){
				baiDuTag = new BaiDuTag();
				baiDuTag.setName(keyword);
				baiDuTag.setCode(CodeUtils.getNumberCode(3));
				while (baiDuTagService.codeExist(baiDuTag.getCode())){
					baiDuTag.setCode(CodeUtils.getNumberCode(3));
				}
				baiDuTagService.save(baiDuTag);
			}
			baiDuResource.getBaiDuTags().add(baiDuTag);
		}
		baiDuResourceService.save(baiDuResource);
		return "ok";
	}

	@GetMapping("aa")
	public String aa() {
		String[] paths = new String[]{
				"F:\\BaiduNetdiskDownload\\新建文件夹\\web前端教程\\【01】2019 web前端课程大全（6套）\\01：2019黑吗WEB前端视频最新课程基础班+就业班（推荐）",
				"F:\\BaiduNetdiskDownload\\新建文件夹\\web前端教程\\【01】2019 web前端课程大全（6套）\\02：2019千锋web前端系列教程（共1300集）\\02. 基础",
				"F:\\BaiduNetdiskDownload\\新建文件夹\\web前端教程\\【01】2019 web前端课程大全（6套）\\02：2019千锋web前端系列教程（共1300集）\\03. 进阶",
				"F:\\BaiduNetdiskDownload\\新建文件夹\\web前端教程\\【01】2019 web前端课程大全（6套）\\02：2019千锋web前端系列教程（共1300集）\\04. 高级",
				"F:\\BaiduNetdiskDownload\\新建文件夹\\web前端教程\\【01】2019 web前端课程大全（6套）\\02：2019千锋web前端系列教程（共1300集）\\05. 项目",
				"F:\\BaiduNetdiskDownload\\新建文件夹\\web前端教程\\【01】2019 web前端课程大全（6套）\\05：其他课程2套（可做补充性学习）\\01：web前端框架进阶教程",
				"F:\\BaiduNetdiskDownload\\新建文件夹\\web前端教程\\【02】大师之路---加薪提升课程（10套）",
				"F:\\BaiduNetdiskDownload\\新建文件夹\\web前端教程\\【03】前端实战系列（18套）\\01：Vue.js教程+实战项目（11套）\\Vue.js 项目",
				"F:\\BaiduNetdiskDownload\\新建文件夹\\web前端教程\\【04】面试题\\01. 1+X技能认证培训",
		};

		for (String path:paths) {
			File parent = new File(path);
			if(parent!=null && parent.isDirectory()){
				File[] files = parent.listFiles();
				for (File file:files) {
					BaiDuResource baiDuResource = new BaiDuResource();
					baiDuResource.setTitle(parent.getName()+"---"+file.getName());
					baiDuResource.setCode(CodeUtils.getNumberCode(5));
					while (baiDuResourceService.codeExist(baiDuResource.getCode())){
						baiDuResource.setCode(CodeUtils.getNumberCode(5));
					}
					baiDuResource.setHits(0L);
					List<String> keywords = HanLPUtils.extractKeyword(baiDuResource.getTitle());
					for (String keyword:keywords) {
						BaiDuTag baiDuTag = baiDuTagService.findByName(keyword);
						if(baiDuTag==null){
							baiDuTag = new BaiDuTag();
							baiDuTag.setName(keyword);
							baiDuTag.setCode(CodeUtils.getNumberCode(3));
							while (baiDuTagService.codeExist(baiDuTag.getCode())){
								baiDuTag.setCode(CodeUtils.getNumberCode(3));
							}
							baiDuTagService.save(baiDuTag);
						}
						baiDuResource.getBaiDuTags().add(baiDuTag);
					}
					baiDuResourceService.save(baiDuResource);
				}
			}
		}



		return "ok";
	}
}