/*
 *	Copyright © 2013 Changsha Shishuo Network Technology Co., Ltd. All rights reserved.
 *	长沙市师说网络科技有限公司 版权所有
 *	http://www.shishuo.com
 */
package com.shishuo.cms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.shishuo.cms.entity.User;
import com.shishuo.cms.entity.vo.UserVo;

/**
 * 用户
 * 
 * @author myc
 */

@Repository
public interface UserDao {

	// ///////////////////////////////
	// ///// 增加 ////////
	// ///////////////////////////////

	/**
	 * 添加用户
	 * 
	 * @param User
	 * @return Integer
	 * 
	 */
	public int addUser(User user);

	// ///////////////////////////////
	// ///// 刪除 ////////
	// ///////////////////////////////

	/**
	 * 删除用户
	 * 
	 * @param userId
	 * @return Integer
	 * 
	 */
	public int deleteUser(@Param("userId") long userId);

	// ///////////////////////////////
	// ///// 修改 ////////
	// ///////////////////////////////

	/**
	 * 修改用户信息
	 * 
	 * @param userId
	 * @param username
	 * @param password
	 * @param nickname
	 * @param name
	 * @return User
	 * @param password
	 * @param user_name
	 */
	public void updateUserByuserId(@Param("userId") long userId,
			@Param("username") String username,
			@Param("password") String password,
			@Param("nickname") String nickname, @Param("name") String name);

	// ///////////////////////////////
	// ///// 查詢 ////////
	// ///////////////////////////////

	/**
	 * 获取所有用户列表
	 * 
	 * @param offset
	 * @param rows
	 * @return List<User>
	 * 
	 */
	public List<User> getAllList(@Param("offset") long offset,
			@Param("rows") long rows);

	/**
	 * 获取所有用户的数量
	 * 
	 * @return Integer
	 * 
	 */
	public int getAllListCount();

	/**
	 * 通过Id获得指定用户资料
	 * 
	 * @param userId
	 * @return User
	 */
	public User getUserById(@Param("userId") long userId);

	/**
	 * 通过username获得指定的用户
	 * 
	 * @param username
	 * @return User
	 * 
	 */
	public UserVo getUserByUsername(@Param("username") String username);

}
