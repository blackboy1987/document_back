
package com.igomall.controller.member;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.entity.BaseEntity;
import com.igomall.entity.member.Message;
import com.igomall.entity.member.Member;
import com.igomall.exception.UnauthorizedException;
import com.igomall.security.CurrentUser;
import com.igomall.service.member.MemberService;
import com.igomall.service.member.MessageService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Controller - 消息
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@RestController("memberApiMessageController")
@RequestMapping("/member/api/message")
public class MessageController extends BaseController {

	/**
	 * 每页记录数
	 */
	private static final int PAGE_SIZE = 10;

	@Autowired
	private MessageService messageService;
	@Autowired
	private MemberService memberService;

	/**
	 * 添加属性
	 */
	@ModelAttribute
	public void populateModel(Long draftMessageId, Long memberMessageId, @CurrentUser Member currentUser, ModelMap model) {
		Message draftMessage = messageService.find(draftMessageId);
		if (draftMessage != null && !currentUser.equals(draftMessage.getSender())) {
			throw new UnauthorizedException();
		}
		model.addAttribute("draftMessage", draftMessage);

		Message memberMessage = messageService.find(memberMessageId);
		if (memberMessage != null && !currentUser.equals(memberMessage.getSender()) && !currentUser.equals(memberMessage.getReceiver())) {
			throw new UnauthorizedException();
		}
		model.addAttribute("memberMessage", memberMessage);
	}

	/**
	 * 检查用户名是否合法
	 */
	@PostMapping("/check_username")
	public @ResponseBody boolean checkUsername(String username, @CurrentUser Member currentUser) {
		return StringUtils.isNotEmpty(username) && !StringUtils.equalsIgnoreCase(username, currentUser.getUsername()) && memberService.usernameExists(username);
	}

	/**
	 * 发送
	 */
	@PostMapping("/send")
	public com.igomall.common.Message send(@ModelAttribute(binding = false, name = "draftMessage") Message draftMessage, String username, String title, String content, @RequestParam(defaultValue = "false") Boolean isDraft, @CurrentUser Member currentUser, HttpServletRequest request) {
		if (!isValid(Message.class, "title", title) || !isValid(Message.class, "content", content)) {
			return com.igomall.common.Message.error("参数错误");
		}
		if (draftMessage != null && draftMessage.getIsDraft()) {
			messageService.delete(draftMessage);
		}
		Member receiver = null;
		if (StringUtils.isNotEmpty(username)) {
			receiver = memberService.findByUsername(username);
			if (currentUser.equals(receiver)) {
				return com.igomall.common.Message.error("参数错误");
			}
		}
		Message message = new Message();
		message.setTitle(title);
		message.setContent(content);
		message.setIp(request.getRemoteAddr());
		message.setIsDraft(isDraft);
		message.setSenderRead(true);
		message.setReceiverRead(false);
		message.setSenderDelete(false);
		message.setReceiverDelete(false);
		message.setSender(currentUser);
		message.setReceiver(receiver);
		message.setForMessage(null);
		message.setReplyMessages(null);
		messageService.save(message);
		return com.igomall.common.Message.success("操作成功");
	}

	/**
	 * 查看
	 */
	@PostMapping("/view")
	public Message view(@ModelAttribute(binding = false, name = "memberMessage") Message memberMessage, @CurrentUser Member currentUser) {
		if (memberMessage == null || memberMessage.getIsDraft() || memberMessage.getForMessage() != null) {
			return memberMessage;
		}
		if ((currentUser.equals(memberMessage.getReceiver()) && memberMessage.getReceiverDelete()) || (currentUser.equals(memberMessage.getSender()) && memberMessage.getSenderDelete())) {
			return new Message();
		}
		if (currentUser.equals(memberMessage.getReceiver())) {
			memberMessage.setReceiverRead(true);
		} else {
			memberMessage.setSenderRead(true);
		}
		messageService.update(memberMessage);
		return memberMessage;
	}

	/**
	 * 回复
	 */
	@PostMapping("/reply")
	public com.igomall.common.Message reply(@ModelAttribute(binding = false, name = "memberMessage") Message memberMessage, String content, @CurrentUser Member currentUser, HttpServletRequest request) {
		if (!isValid(Message.class, "content", content)) {
			return com.igomall.common.Message.error("参数错误");
		}
		if (memberMessage == null || memberMessage.getIsDraft() || memberMessage.getForMessage() != null) {
			return com.igomall.common.Message.error("参数错误");
		}
		if ((currentUser.equals(memberMessage.getReceiver()) && memberMessage.getReceiverDelete()) || (currentUser.equals(memberMessage.getSender()) && memberMessage.getSenderDelete())) {
			return com.igomall.common.Message.error("参数错误");
		}
		Message message = new Message();
		message.setTitle("reply: " + memberMessage.getTitle());
		message.setContent(content);
		message.setIp(request.getRemoteAddr());
		message.setIsDraft(false);
		message.setSenderRead(true);
		message.setReceiverRead(false);
		message.setSenderDelete(false);
		message.setReceiverDelete(false);
		message.setSender(currentUser);
		message.setReceiver(currentUser.equals(memberMessage.getReceiver()) ? memberMessage.getSender() : memberMessage.getReceiver());
		message.setForMessage(null);
		message.setReplyMessages(null);
		if ((currentUser.equals(memberMessage.getReceiver()) && !memberMessage.getSenderDelete()) || (currentUser.equals(memberMessage.getSender()) && !memberMessage.getReceiverDelete())) {
			message.setForMessage(memberMessage);
		}
		messageService.save(message);

		if (currentUser.equals(memberMessage.getSender())) {
			memberMessage.setSenderRead(true);
			memberMessage.setReceiverRead(false);
		} else {
			memberMessage.setSenderRead(false);
			memberMessage.setReceiverRead(true);
		}
		messageService.update(memberMessage);

		if ((currentUser.equals(memberMessage.getReceiver()) && !memberMessage.getSenderDelete()) || (currentUser.equals(memberMessage.getSender()) && !memberMessage.getReceiverDelete())) {
			return com.igomall.common.Message.success("操作成功");
		} else {
			return com.igomall.common.Message.success("操作成功");
		}
	}

	/**
	 * 列表
	 */
	@PostMapping("/list")
	@JsonView(BaseEntity.BaseView.class)
	public Page<Message> list(Pageable pageable, @CurrentUser Member currentUser) {
		return messageService.findPage(currentUser, pageable);
	}

	/**
	 * 草稿箱
	 */
	@GetMapping("/draft")
	public Page<Message> draft(Pageable pageable, @CurrentUser Member currentUser) {
		return messageService.findDraftPage(currentUser, pageable);
	}

	/**
	 * 删除
	 */
	@PostMapping("/delete")
	public com.igomall.common.Message delete(Long messageId, @CurrentUser Member currentUser) {
		messageService.delete(messageId, currentUser);
		return com.igomall.common.Message.success("操作成功");
	}

}