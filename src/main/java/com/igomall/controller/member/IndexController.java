
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
		data.put("address",member.getAddress());
		data.put("areaIds",member.getAreaIds());
		data.put("birth",member.getBirth());
		data.put("email",member.getEmail());
		data.put("gender",member.getGender());
		data.put("mobile",member.getMobile());
		data.put("phone",member.getPhone());
		data.put("signature",member.getSignature());
		data.put("job",member.getJob());
		data.put("school",member.getSchool());
		data.put("major",member.getMajor());
		data.put("tags",member.getTags());
		data.put("memberRankName",member.getMemberRank().getName());
		if(member.getArea()!=null){
			data.put("area",member.getArea().getFullName());
		}
		data.put("id",member.getId());
		return data;
	}

}