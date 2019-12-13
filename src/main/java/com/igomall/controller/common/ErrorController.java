
package com.igomall.controller.common;

import com.igomall.common.Results;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Controller - 错误
 * 
 * @author blackboy
 * @version 1.0
 */
@RestController("commonErrorController")
@RequestMapping("/common/error")
public class ErrorController {

	/**
	 * 无此访问权限
	 */
	@GetMapping("/unauthorized")
	public ResponseEntity<Map<String, String>> unauthorized() {
		return Results.unauthorized("无访问权限");
	}

	/**
	 * 资源未找到
	 */
	@GetMapping("/not_found")
	public String notFound() {
		return "common/error/not_found";
	}

	/**
	 * 验证码错误
	 */
	@GetMapping("/ncorrect_captcha")
	public String ncorrectCaptcha() {
		return "common/error/ncorrect_captcha";
	}

	/**
	 * CSRF令牌错误
	 */
	@GetMapping("/invalid_csrf_token")
	public String invalidCsrfToken() {
		return "common/error/invalid_csrf_token";
	}

}