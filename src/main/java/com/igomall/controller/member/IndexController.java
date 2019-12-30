
package com.igomall.controller.member;

import com.igomall.entity.member.Member;
import com.igomall.security.CurrentUser;
import com.igomall.security.UserAuthenticationToken;
import com.igomall.service.UserService;
import com.igomall.service.member.MemberService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller - 会员登录
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@RestController("memberIndexController")
@RequestMapping("/member/api")
public class IndexController extends BaseController {

	@PostMapping("/currentUser")
	public Map<String,Object> currentUser(@CurrentUser Member member){
		Map<String,Object> data = new HashMap<>();
		data.put("username",member.getUsername());
		data.put("avatar",member.getAvatar());
		data.put("name",member.getName());
		data.put("id",member.getId());
		return data;
	}

}