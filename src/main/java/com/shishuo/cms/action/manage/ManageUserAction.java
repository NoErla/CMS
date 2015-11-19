/*
 *	Copyright © 2013 Changsha Shishuo Network Technology Co., Ltd. All rights reserved.
 *	长沙市师说网络科技有限公司 版权所有
 *	http://www.shishuo.com
 */

package com.shishuo.cms.action.manage;

import com.shishuo.cms.entity.User;
import com.shishuo.cms.entity.vo.JsonVo;
import com.shishuo.cms.util.SSUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户action
 * 
 * @author myc
 */
@Controller
@RequestMapping("/manage/user")
public class ManageUserAction extends ManageBaseAction {

	/**
	 * 进入添加user页面
	 */
	@RequestMapping(value = "/add.htm", method = RequestMethod.GET)
	public String addUser(ModelMap modelMap) {
		modelMap.put("username", "");
		modelMap.put("password", "");
		modelMap.put("nickname", "");
		modelMap.put("name", "");
		return "manage/user/add";
	}

	/**
	 * 进入用户管理页面
	 */
	@RequestMapping(value = "/manage.htm", method = RequestMethod.GET)
	public String manage(
			@RequestParam(value = "p", defaultValue = "1") int pageNum,
			ModelMap modelMap) {
		modelMap.put("pageVo", userService.getAllListPage(pageNum));
		return "manage/user/manage";
	}

	/**
	 * 添加User
	 */
	@ResponseBody
	@RequestMapping(value = "/addNew.json", method = RequestMethod.POST)
	public JsonVo<String> addNewUser(
			@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password,
			@RequestParam(value = "nickname", required = false) String nickname,
			@RequestParam(value = "name", required = false) String name) {
		JsonVo<String> json = new JsonVo<String>();
		User user = userService.getUserByUsername(username);
		if (user != null) {
			json.getErrors().put("username", "用户名不能重复");
		}
		try {
			if (StringUtils.isBlank(username)) {
				json.getErrors().put("username", "用户名称不能为空");
			}
			if (StringUtils.isBlank(password)) {
				json.getErrors().put("password", "密码不能为空");
			} else if (password.length() < 6) {
				json.getErrors().put("password", "密码不能小于6位");
			} else if (password.length() > 16) {
				json.getErrors().put("password", "密码不能大于16位");
			}
			// 检测校验结果
			validate(json);
			userService.addUser(SSUtils.toText(username.trim()), password,
					nickname, name);
			json.setResult(true);
		} catch (Exception e) {
			json.setResult(false);
			json.setMsg(e.getMessage());
		}
		return json;
	}

	/**
	 * 进入用户列表页面
	 */
	@RequestMapping(value = "/page.htm", method = RequestMethod.GET)
	public String allList(
			@RequestParam(value = "p", defaultValue = "1") int pageNum,
			ModelMap modelMap) {
		modelMap.put("pageVo", userService.getAllListPage(pageNum));
		return "manage/user/all";
	}

	/**
	 * 进入单个user页面
	 */
	@RequestMapping(value = "/update.htm", method = RequestMethod.GET)
	public String update(
			@RequestParam(value = "userId", defaultValue = "0") long userId,
			ModelMap modelMap, HttpServletRequest request) {
		User user = userService.getUserById(userId);
		modelMap.put("user", user);
		return "manage/user/update";
	}

	/**
	 * 修改指定的user资料
	 */
	@ResponseBody
	@RequestMapping(value = "/update.json", method = RequestMethod.POST)
	public JsonVo<String> updateUser(
			@RequestParam(value = "userId") long userId,
			@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password,
			@RequestParam(value = "nickname") String nickname,
			@RequestParam(value = "name") String name,
			HttpServletRequest request) {
		JsonVo<String> json = new JsonVo<String>();
		try {
				if (StringUtils.isBlank(password)) {
					json.getErrors().put("password", "密码不能为空");
				}
				if (password.length() < 6) {
					json.getErrors().put("password", "密码不能小于6位数");
				}
				if (password.length() > 18) {
					json.getErrors().put("password", "密码不能大于18位数");
				}

			// 检测校验结果
			validate(json);
			userService.updateUserByUserId(userId, username,
					SSUtils.toText(password), nickname, name);
			json.setResult(true);
		} catch (Exception e) {
			json.setResult(false);
			json.setMsg(e.getMessage());
		}
		return json;
	}

	/**
	 * 删除用户
	 */

	@ResponseBody
	@RequestMapping(value = "/delete.json", method = RequestMethod.POST)
	public JsonVo<String> delete(@RequestParam(value = "userId") long userId,
			HttpServletRequest request) {
		JsonVo<String> json = new JsonVo<String>();
		try {
			userService.deleteUser(userId);
			json.setResult(true);
		} catch (Exception e) {
			json.setResult(false);
			json.setMsg(e.getMessage());
		}
		return json;
	}

}
