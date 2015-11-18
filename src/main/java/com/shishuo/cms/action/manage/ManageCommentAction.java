/*
 *	Copyright © 2013 Changsha Shishuo Network Technology Co., Ltd. All rights reserved.
 *	长沙市师说网络科技有限公司 版权所有
 *	http://www.shishuo.com
 */

package com.shishuo.cms.action.manage;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


import com.shishuo.cms.constant.CommentConstant;
import com.shishuo.cms.entity.Admin;
import com.shishuo.cms.entity.Comment;
import com.shishuo.cms.entity.vo.AdminVo;
import com.shishuo.cms.entity.vo.CommentVo;
import com.shishuo.cms.entity.vo.JsonVo;
import com.shishuo.cms.entity.vo.PageVo;
import com.shishuo.cms.exception.CommentNotFoundException;
import com.shishuo.cms.util.SSUtils;

/**
 * @author 评论管理action
 * 
 */
@Controller
@RequestMapping("/manage/comment")
public class ManageCommentAction extends ManageBaseAction {


	/**
	 * @author 进入某种评论的列表分页的首页
	 * 
	 */
	@RequestMapping(value = "/page.htm", method = RequestMethod.GET)
	public String list(
			@RequestParam(value = "p", defaultValue = "1") int pageNum,
			@RequestParam(value = "status", defaultValue = "display") CommentConstant.Status status,
			HttpServletRequest request, ModelMap modelMap)
			 {
		PageVo<CommentVo> pageVo = commentService.getCommentPageByStatus(
				status, pageNum);
		int hiddenCount = commentService.getCommentCountByStatus(CommentConstant.Status.hidden);
		int displayCount = commentService.getCommentCountByStatus(CommentConstant.Status.display);
		int trashCount = commentService.getCommentCountByStatus(CommentConstant.Status.trash);
		modelMap.put("hiddenCount", hiddenCount);
		modelMap.put("displayCount", displayCount);
		modelMap.put("trashCount", trashCount);
		modelMap.put("pageVo", pageVo);
		modelMap.put("statusType", status);
		modelMap.put("p", pageNum);
		return "manage/comment/all";
	}
	
	/**
	 * 进入单个评论界面
	 * @return
	 * @throws CommentNotFoundException 
	 */
	@RequestMapping(value = "/detail.htm", method = RequestMethod.GET)
	public String detail(
			@RequestParam(value = "commentId", defaultValue = "0") long commentId,
			ModelMap modelMap) throws CommentNotFoundException{
		CommentVo commentVo = commentService.getCommentById(commentId);
		modelMap.put("comment", commentVo);
		return "manage/comment/detail";
		
	}
	
	/**
	 * @author 修改评论审核状态
	 * @throws ArticleNotFoundException
	 * 
	 */
	@ResponseBody
	@RequestMapping(value = "/check.json", method = RequestMethod.POST)
	public JsonVo<String> check(
			@RequestParam(value = "commentId") long commentId,
			@RequestParam(value = "status") CommentConstant.Status status,
			HttpServletRequest request) throws CommentNotFoundException {
		JsonVo<String> json = new JsonVo<String>();
		AdminVo admin = this.getAdmin(request);
		if (!admin.getIsAdmin()) {
			json.setResult(false);
			json.setMsg("您不是超级管理员，无权该审核文件！");
		} else {
			commentService.updateStatus(commentId, status);
			json.setResult(true);
		}
		return json;
	}
	
	/**
	 * 修改评论内容
	 * @param commentId
	 * @param name
	 * @param userId
	 * @param content
	 * @param url
	 * @param ip
	 * @param request
	 * @return
	 * @throws CommentNotFoundException
	 */
	@ResponseBody
	@RequestMapping(value = "/update.json", method = RequestMethod.POST)
	public JsonVo<String> update(
			@RequestParam(value = "commentId") long commentId,
			@RequestParam(value = "name") String name,
			@RequestParam(value = "userId") long userId,
			@RequestParam(value = "content") String content,
			@RequestParam(value = "url") String url,
			@RequestParam(value = "ip") String ip,
			HttpServletRequest request) throws CommentNotFoundException {
		JsonVo<String> json = new JsonVo<String>();
		try{
		commentService.updateCommentByCommentId(commentId, name, userId, content, url, ip);
		json.setResult(true);
		}catch(Exception e){
			json.setResult(false);
			json.setMsg(e.getMessage());
		}
		return json;
	}
	
	/**
	 * @author 根据userId查询评论
	 * 
	 */
	@RequestMapping(value = "/findByFatherId.htm", method = RequestMethod.GET)
	public String findByFatherId(
			@RequestParam(value = "p", defaultValue = "1") int pageNum,
			@RequestParam(value = "fatherId") long fatherId,
			HttpServletRequest request, ModelMap modelMap)
			 {
		PageVo<CommentVo> pageVo = commentService.getCommentPageByFatherId(
				fatherId, pageNum);
		int hiddenCount = commentService.getCommentCountByStatus(CommentConstant.Status.hidden);
		int displayCount = commentService.getCommentCountByStatus(CommentConstant.Status.display);
		int trashCount = commentService.getCommentCountByStatus(CommentConstant.Status.trash);
		modelMap.put("hiddenCount", hiddenCount);
		modelMap.put("displayCount", displayCount);
		modelMap.put("trashCount", trashCount);
		modelMap.put("pageVo", pageVo);
		modelMap.put("statusType", "findByFatherId");
		modelMap.put("p", pageNum);
		return "manage/comment/all";
	}
	
}
