
package com.igomall.service.member.impl;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import com.igomall.service.impl.BaseServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.igomall.common.CommonAttributes;
import com.igomall.common.Filter;
import com.igomall.common.Order;
import com.igomall.dao.member.MemberAttributeDao;
import com.igomall.dao.member.MemberDao;
import com.igomall.entity.member.Member;
import com.igomall.entity.member.MemberAttribute;
import com.igomall.service.member.MemberAttributeService;

/**
 * Service - 会员注册项
 *
 * @author IGOMALL  Team
 * @version 1.0
 */
@Service
public class MemberAttributeServiceImpl extends BaseServiceImpl<MemberAttribute, Long> implements MemberAttributeService {

	@Autowired
	private MemberAttributeDao memberAttributeDao;
	@Autowired
	private MemberDao memberDao;

	@Transactional(readOnly = true)
	public Integer findUnusedPropertyIndex() {
		return memberAttributeDao.findUnusedPropertyIndex();
	}

	@Transactional(readOnly = true)
	public List<MemberAttribute> findList(Boolean isEnabled, Integer count, List<Filter> filters, List<Order> orders) {
		return memberAttributeDao.findList(isEnabled, count, filters, orders);
	}

	@Transactional(readOnly = true)
	@Cacheable(value = "memberAttribute", condition = "#useCache")
	public List<MemberAttribute> findList(Boolean isEnabled, Integer count, List<Filter> filters, List<Order> orders, boolean useCache) {
		return memberAttributeDao.findList(isEnabled, count, filters, orders);
	}

	@Transactional(readOnly = true)
	@Cacheable(value = "memberAttribute", condition = "#useCache")
	public List<MemberAttribute> findList(Boolean isEnabled, boolean useCache) {
		return memberAttributeDao.findList(isEnabled, null, null, null);
	}

	@Transactional(readOnly = true)
	public boolean isValid(MemberAttribute memberAttribute, String[] values) {
		Assert.notNull(memberAttribute,"");
		Assert.notNull(memberAttribute.getType(),"");

		String value = ArrayUtils.isNotEmpty(values) ? values[0].trim() : null;
		switch (memberAttribute.getType()) {
		case name:
		case address:
		case zipCode:
		case phone:
		case text:
			if (memberAttribute.getIsRequired() && StringUtils.isEmpty(value)) {
				return false;
			}
			if (StringUtils.isNotEmpty(memberAttribute.getPattern()) && StringUtils.isNotEmpty(value) && !Pattern.compile(memberAttribute.getPattern()).matcher(value).matches()) {
				return false;
			}
			break;
		case gender:
			if (memberAttribute.getIsRequired() && StringUtils.isEmpty(value)) {
				return false;
			}
			if (StringUtils.isNotEmpty(value)) {
				try {
					Member.Gender.valueOf(value);
				} catch (IllegalArgumentException e) {
					return false;
				}
			}
			break;
		case birth:
			if (memberAttribute.getIsRequired() && StringUtils.isEmpty(value)) {
				return false;
			}
			if (StringUtils.isNotEmpty(value)) {
				try {
					DateUtils.parseDate(value, CommonAttributes.DATE_PATTERNS);
				} catch (ParseException e) {
					return false;
				}
			}
			break;
		case select:
			if (memberAttribute.getIsRequired() && StringUtils.isEmpty(value)) {
				return false;
			}
			if (CollectionUtils.isEmpty(memberAttribute.getOptions())) {
				return false;
			}
			if (StringUtils.isNotEmpty(value) && !memberAttribute.getOptions().contains(value)) {
				return false;
			}
			break;
		case checkbox:
			if (memberAttribute.getIsRequired() && ArrayUtils.isEmpty(values)) {
				return false;
			}
			if (CollectionUtils.isEmpty(memberAttribute.getOptions())) {
				return false;
			}
			if (ArrayUtils.isNotEmpty(values) && !memberAttribute.getOptions().containsAll(Arrays.asList(values))) {
				return false;
			}
			break;
		}
		return true;
	}

	@Transactional(readOnly = true)
	public Object toMemberAttributeValue(MemberAttribute memberAttribute, String[] values) {
		Assert.notNull(memberAttribute,"");
		Assert.notNull(memberAttribute.getType(),"");

		if (ArrayUtils.isEmpty(values)) {
			return null;
		}

		String value = values[0].trim();
		switch (memberAttribute.getType()) {
		case name:
		case address:
		case zipCode:
		case phone:
		case text:
			return StringUtils.isNotEmpty(value) ? value : null;
		case gender:
			if (StringUtils.isEmpty(value)) {
				return null;
			}
			try {
				return Member.Gender.valueOf(value);
			} catch (IllegalArgumentException e) {
				return null;
			}
		case birth:
			if (StringUtils.isEmpty(value)) {
				return null;
			}
			try {
				return DateUtils.parseDate(value, CommonAttributes.DATE_PATTERNS);
			} catch (ParseException e) {
				return null;
			}
		case select:
			if (CollectionUtils.isNotEmpty(memberAttribute.getOptions()) && memberAttribute.getOptions().contains(value)) {
				return value;
			}
			break;
		case checkbox:
			if (CollectionUtils.isNotEmpty(memberAttribute.getOptions()) && memberAttribute.getOptions().containsAll(Arrays.asList(values))) {
				return Arrays.asList(values);
			}
			break;
		}
		return null;
	}

	@Override
	@Transactional
	@CacheEvict(value = "memberAttribute", allEntries = true)
	public MemberAttribute save(MemberAttribute memberAttribute) {
		Assert.notNull(memberAttribute,"");

		Integer unusedPropertyIndex = memberAttributeDao.findUnusedPropertyIndex();
		Assert.notNull(unusedPropertyIndex,"");

		memberAttribute.setPropertyIndex(unusedPropertyIndex);

		return super.save(memberAttribute);
	}

	@Override
	@Transactional
	@CacheEvict(value = "memberAttribute", allEntries = true)
	public MemberAttribute update(MemberAttribute memberAttribute) {
		return super.update(memberAttribute);
	}

	@Override
	@Transactional
	@CacheEvict(value = "memberAttribute", allEntries = true)
	public MemberAttribute update(MemberAttribute memberAttribute, String... ignoreProperties) {
		return super.update(memberAttribute, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = "memberAttribute", allEntries = true)
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = "memberAttribute", allEntries = true)
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = "memberAttribute", allEntries = true)
	public void delete(MemberAttribute memberAttribute) {
		if (memberAttribute != null) {
			memberDao.clearAttributeValue(memberAttribute);
		}

		super.delete(memberAttribute);
	}

}
