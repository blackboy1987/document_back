
package com.igomall.dao.impl;

import org.springframework.stereotype.Repository;

import com.igomall.dao.UserDao;
import com.igomall.entity.User;

/**
 * Dao - 用户
 * 
 * @author blackboy
 * @version 1.0
 */
@Repository
public class UserDaoImpl extends BaseDaoImpl<User, Long> implements UserDao {

}