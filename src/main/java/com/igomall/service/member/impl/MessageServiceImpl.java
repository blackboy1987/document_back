
package com.igomall.service.member.impl;

import com.igomall.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.dao.member.MessageDao;
import com.igomall.entity.member.Member;
import com.igomall.entity.member.Message;
import com.igomall.service.member.MessageService;

/**
 * Service - 消息
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@Service
public class MessageServiceImpl extends BaseServiceImpl<Message, Long> implements MessageService {

	@Autowired
	private MessageDao messageDao;

	@Transactional(readOnly = true)
	public Page<Message> findPage(Member member, Pageable pageable) {
		return messageDao.findPage(member, pageable);
	}

	@Transactional(readOnly = true)
	public Page<Message> findDraftPage(Member sender, Pageable pageable) {
		return messageDao.findDraftPage(sender, pageable);
	}

	@Transactional(readOnly = true)
	public Long count(Member member, Boolean read) {
		return messageDao.count(member, read);
	}

	public void delete(Long id, Member member) {
		messageDao.remove(id, member);
	}

}