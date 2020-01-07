
package com.igomall.controller.member;

import com.igomall.common.Message;
import com.igomall.entity.member.Member;
import com.igomall.entity.member.MemberAttribute;
import com.igomall.security.CurrentUser;
import com.igomall.service.member.MemberAttributeService;
import com.igomall.service.member.MemberService;
import com.igomall.service.setting.AreaService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.functors.AndPredicate;
import org.apache.commons.collections.functors.UniquePredicate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Controller - 个人资料
 * 
 * @author blackboy
 * @version 1.0
 */
@RestController("memberProfileController")
@RequestMapping("/member/api/profile")
public class ProfileController extends BaseController {

	@Autowired
	private MemberService memberService;
	@Autowired
	private MemberAttributeService memberAttributeService;
	@Autowired
	private AreaService areaService;

	/**
	 * 检查E-mail是否唯一
	 */
	@GetMapping("/check_email")
	public @ResponseBody boolean checkEmail(String email, @CurrentUser Member currentUser) {
		return StringUtils.isNotEmpty(email) && memberService.emailUnique(currentUser.getId(), email);
	}

	/**
	 * 检查手机是否唯一
	 */
	@GetMapping("/check_mobile")
	public @ResponseBody boolean checkMobile(String mobile, @CurrentUser Member currentUser) {
		return StringUtils.isNotEmpty(mobile) && memberService.mobileUnique(currentUser.getId(), mobile);
	}

	/**
	 * 编辑
	 */
	@GetMapping("/edit")
	public String edit(ModelMap model) {
		model.addAttribute("genders", Member.Gender.values());
		return "member/profile/edit";
	}

	/**
	 * 更新
	 */
	@PostMapping("/update1")
	public String update(String email, String mobile, @CurrentUser Member currentUser, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		if (!isValid(Member.class, "email", email) || !isValid(Member.class, "mobile", mobile)) {
			return UNPROCESSABLE_ENTITY_VIEW;
		}
		if (!memberService.emailUnique(currentUser.getId(), email)) {
			return UNPROCESSABLE_ENTITY_VIEW;
		}
		if (!memberService.mobileUnique(currentUser.getId(), mobile)) {
			return UNPROCESSABLE_ENTITY_VIEW;
		}
		currentUser.setEmail(email);
		currentUser.setMobile(mobile);
		currentUser.removeAttributeValue();
		for (MemberAttribute memberAttribute : memberAttributeService.findList(true, true)) {
			String[] values = request.getParameterValues("memberAttribute_" + memberAttribute.getId());
			if (!memberAttributeService.isValid(memberAttribute, values)) {
				return UNPROCESSABLE_ENTITY_VIEW;
			}
			Object memberAttributeValue = memberAttributeService.toMemberAttributeValue(memberAttribute, values);
			currentUser.setAttributeValue(memberAttribute, memberAttributeValue);
		}
		memberService.update(currentUser);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:edit";
	}
	/**
	 * 更新
	 */
	@PostMapping("/update_avatar")
	public Message updateAvatar(String avatar, @CurrentUser Member currentUser) {
		currentUser.setAvatar(avatar);
		memberService.update(currentUser);
		return Message.success("头像修改成功");
	}

	@PostMapping("/info")
	public Map<String,Object> info(@CurrentUser Member member){
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
		data.put("memberRankName",member.getMemberRank().getName());
		data.put("phone",member.getPhone());
		data.put("signature",member.getSignature());
		data.put("job",member.getJob());
		data.put("school",member.getSchool());
		data.put("major",member.getMajor());
		data.put("tags",member.getTags());
		return data;
	}

	/**
	 * 更新
	 */
	@PostMapping("/update")
	public Message update(String tags, String name, Member.Gender gender, String email, Long areaId, String signature, String job, String school, String major, @CurrentUser Member currentUser) {
		if(StringUtils.isNotEmpty(tags)){
			List<String> tags1 = Arrays.asList(tags.split(","));
			CollectionUtils.filter(tags1, new AndPredicate(new UniquePredicate(), new Predicate() {
				public boolean evaluate(Object object) {
					String option = (String) object;
					return StringUtils.isNotEmpty(option);
				}
			}));
			currentUser.setTags(tags1);
		}
		if(StringUtils.isNotEmpty(name)){
			currentUser.setName(name);;
		}
		if(!memberService.emailUnique(currentUser.getId(), email)){
			return Message.error("邮箱已被占用");
		}
		currentUser.setGender(gender);
		currentUser.setEmail(email);
		currentUser.setSignature(signature);
		currentUser.setJob(job);
		currentUser.setSchool(school);
		currentUser.setMajor(major);
		currentUser.setArea(areaService.find(areaId));
		memberService.update(currentUser);
		return Message.success("资料更新成功");
	}
}