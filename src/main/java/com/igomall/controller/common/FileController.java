
package com.igomall.controller.common;

import com.igomall.common.FileType;
import com.igomall.common.Message;
import com.igomall.controller.admin.BaseController;
import com.igomall.service.FileService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller - 文件
 * 
 * @author blackboy
 * @version 1.0
 */
@RestController("commonFileController")
@RequestMapping("/common/file")
@CrossOrigin
public class FileController extends BaseController {

	@Autowired
	private FileService fileService;

	/**
	 * 上传
	 */
	@GetMapping("/upload")
	public @ResponseBody Map<String, Object> upload() {
		Map<String, Object> data = new HashMap<>();
		data.put("message", SUCCESS_MESSAGE);
		data.put("state", "SUCCESS");
		return data;
	}

	/**
	 * 上传
	 */
	@PostMapping("/upload")
	public @ResponseBody Map<String, Object> upload(FileType fileType, MultipartFile file,String type) {
		Map<String, Object> data = new HashMap<>();
		if (fileType == null || file == null || file.isEmpty()) {
			data.put("message", ERROR_MESSAGE);
			data.put("state", ERROR_MESSAGE);
			return data;
		}
		if (!fileService.isValid(fileType, file)) {
			data.put("message", Message.warn("admin.upload.invalid"));
			data.put("state", message("admin.upload.invalid"));
			return data;
		}
		String url = fileService.upload(fileType, file, false,type);
		if (StringUtils.isEmpty(url)) {
			data.put("message", Message.warn("admin.upload.error"));
			data.put("state", message("admin.upload.error"));
			return data;
		}
		data.put("message", SUCCESS_MESSAGE);
		data.put("state", "SUCCESS");
		data.put("url", url);
		return data;
	}

}