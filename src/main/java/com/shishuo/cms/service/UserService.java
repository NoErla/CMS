/*
 *	Copyright © 2013 Changsha Shishuo Network Technology Co., Ltd. All rights reserved.
 *	长沙市师说网络科技有限公司 版权所有
 *	http://www.shishuo.com
 */

package com.shishuo.cms.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shishuo.cms.constant.SystemConstant;
import com.shishuo.cms.dao.UserDao;
import com.shishuo.cms.entity.User;
import com.shishuo.cms.entity.vo.PageVo;
import com.shishuo.cms.entity.vo.UserVo;
import com.shishuo.cms.exception.AuthException;
import com.shishuo.cms.util.AuthUtils;
import com.shishuo.cms.util.PropertyUtils;

/**
 * 用户
 * 
 * @author myc
 * 
 */
@Service
public class UserService {

	@Autowired
	private UserDao userDao;

	// ///////////////////////////////
	// ///// 增加 ////////
	// ///////////////////////////////

	/**
	 * 添加用户
	 * 
	 * @param username
	 * @param password
	 * @param nickname
	 * @param name
	 * @return User
	 */
	public User addUser(String username, String password,String nickname,String name)
			throws AuthException {
		Date now = new Date();
		User user = new User();
		user.setUsername(username);
		user.setPassword(AuthUtils.getPassword(password));
		user.setNickname(nickname);
		user.setName(name);
		user.setCreateTime(now);
		userDao.addUser(user);
		return user;
	}

	// ///////////////////////////////
	// ///// 刪除 ////////
	// ///////////////////////////////

	/**
	 * 删除用户
	 * 
	 * @param userId
	 * @return Integer
	 */
	public int deleteUser(long userId) {
		return userDao.deleteUser(userId);
	}

	// ///////////////////////////////
	// ///// 修改 ////////
	// ///////////////////////////////

	/**
	 * 修改用户资料
	 * 
	 * @param userId
	 * @param username
	 * @param password
	 * @param nickname
	 * @param name
	 * @return User
	 * @throws AuthException
	 */

	public void updateUserByUserId(long userId, String username,String password,String nickname,String name)
			throws AuthException {
		String user_username = username;
		String user_password = AuthUtils.getPassword(password);
		String user_nickame = nickname;
		String user_name = name;
		userDao.updateUserByuserId(userId, user_username,user_password,user_nickame,user_name);
	}

	// ///////////////////////////////
	// ///// 查詢 ////////
	// ///////////////////////////////

	/**
	 * 用户登陆
	 * 
	 * @param password
	 * @param request
	 * @throws IOException
	 */
	public void userLogin(String username, String password,
			HttpServletRequest request) throws AuthException,
			IOException {
		UserVo user = userDao.getUserByUsername(username);
		if (user == null) {
			throw new AuthException("用户名或密码错误");
		}
		String loginPassword = AuthUtils.getPassword(password);
		if (loginPassword.equals(user.getPassword())) {
			HttpSession session = request.getSession();
			user.setPassword("");
			if (user.equals(PropertyUtils
					.getValue("shishuocms.user"))) {
				user.setUser(true);
			} else {
				user.setUser(false);
			}
			session.setAttribute(SystemConstant.SESSION_USER,
					user);
		} else {
			throw new AuthException("用户名或密码错误");
		}
	}

	/**
	 * 通过Id获得指定用户资料
	 */
	public User getUserById(long userId) {
		return userDao.getUserById(userId);
	}
	/**
	 * 通过username获得指定用户资料
	 */
	public User getUserByUsername(String username) {
		return userDao.getUserByUsername(username);
	}

	/**
	 * 获得所有用户的分页数据
	 * 
	 * @param offset
	 * @param rows
	 * @return List<User>
	 */
	public List<User> getAllList(long offset, long rows) {
		return userDao.getAllList(offset, rows);
	}

	/**
	 * 获得所有用户的数量
	 * 
	 * @return Integer
	 */
	public int getAllListCount() {
		return userDao.getAllListCount();
	}

	/**
	 * 获得所有用户的分页
	 * 
	 * @param Integer
	 * @return PageVo<User>
	 */
	public PageVo<User> getAllListPage(int pageNum) {
		PageVo<User> pageVo = new PageVo<User>(pageNum);
		pageVo.setRows(20);
		List<User> list = this.getAllList(pageVo.getOffset(),
				pageVo.getRows());
		pageVo.setList(list);
		pageVo.setCount(this.getAllListCount());
		return pageVo;
	}




}
