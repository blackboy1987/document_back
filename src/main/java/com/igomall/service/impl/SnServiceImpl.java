
package com.igomall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.igomall.dao.SnDao;
import com.igomall.entity.Sn;
import com.igomall.service.SnService;

/**
 * Service - 序列号
 * 
 * @author blackboy
 * @version 1.0
 */
@Service
public class SnServiceImpl implements SnService {

	@Autowired
	private SnDao snDao;

	@Transactional
	@Override
	public String generate(Sn.Type type) {
		return snDao.generate(type);
	}

}