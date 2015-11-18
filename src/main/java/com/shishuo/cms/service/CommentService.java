/*
 *	Copyright © 2013 Changsha Shishuo Network Technology Co., Ltd. All rights reserved.
 *	长沙市师说网络科技有限公司 版权所有
 *	http://www.shishuo.com
 */

package com.shishuo.cms.service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.shishuo.cms.constant.CommentConstant;
import com.shishuo.cms.constant.CommentConstant.Status;
import com.shishuo.cms.constant.FolderConstant;
import com.shishuo.cms.dao.CommentDao;
import com.shishuo.cms.entity.Comment;
import com.shishuo.cms.entity.vo.CommentVo;
import com.shishuo.cms.entity.vo.PageVo;
import com.shishuo.cms.exception.CommentNotFoundException;
import com.shishuo.cms.exception.UploadException;
import com.shishuo.cms.util.MediaUtils;

/**
 * 
 * 评论service
 * 
 * @author myc
 * 
 */
@Service
public class CommentService {

	@Autowired
	private CommentDao commentDao;



	// ///////////////////////////////
	// ///// 增加 ////////
	// ///////////////////////////////

	/**
	 * 
	 * @param userId
	 * @param fatherId
	 * @param name
	 * @param url
	 * @param content
	 * @param ip
	 * @param status
	 * @param createTime
	 * @return
	 * @throws UploadException
	 * @throws IOException
	 */
	@CacheEvict(value = "comment", allEntries = true)
	public Comment addComment(long userId, long fatherId,
			String name,String url,String content,
			String ip,CommentConstant.Status status,
			String createTime)
			throws UploadException,IOException {
		Comment comment = new Comment();
		Date now = new Date();
		
		comment.setUserId(userId);
		comment.setFatherId(fatherId);
		comment.setName(name);
		comment.setUrl(url);
		comment.setContent(content);
		comment.setStatus(status);
		if (StringUtils.isBlank(createTime)) {
			comment.setCreateTime(now);
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat(
					"yyyy-MM-dd");
			Date date;
			try {
				date = sdf.parse(createTime);
			} catch (ParseException e) {
				date = now;
			}
			comment.setCreateTime(date);
		}
		commentDao.addComment(comment);
		return commentDao.getCommentById(comment.getCommentId());
	}

	// ///////////////////////////////
	// ///// 刪除 ////////
	// ///////////////////////////////

	/**
	 * 删除评论
	 * 
	 * @param commentId
	 * @return boolean
	 */
	@CacheEvict(value = "comment", allEntries = true)
	public boolean deleteCommentById(long commentId) {
		return commentDao.deleteCommentById(commentId);
	}

	// ///////////////////////////////
	// ///// 修改 ////////
	// ///////////////////////////////
	/**
	 * 改变评论审核状态
	 * @param commentId
	 * @param status
	 */
	@CacheEvict(value = "comment", allEntries = true)
	public void updateStatus(long commentId, Status status) {
		commentDao.updateStatus(commentId, status);
		
	}
	
	/**
	 * 修改评论
	 * @param commentId
	 * @param name
	 * @param userId
	 * @param content
	 * @param url
	 * @param ip
	 */
	@CacheEvict(value = "comment", allEntries = true)
	public void updateCommentByCommentId(long commentId,String name,long userId,
			String content,String url,String ip) {
		commentDao.updateCommentByCommentId(commentId, name, userId, content, url, ip);	
	}
	// ///////////////////////////////
	// ///// 查詢 ////////
	// ///////////////////////////////

	/**
	 * 得到评论
	 * 
	 * @param commentId
	 * @return comment
	 */
	@Cacheable(value = "comment", key = "'getCommentById_'+#commentId")
	public CommentVo getCommentById(long commentId) 
			throws CommentNotFoundException{
		CommentVo commentVo = commentDao.getCommentById(commentId);
		if (commentVo == null) {
			throw new CommentNotFoundException(commentId
					+ " 评论，不存在");
		} else {
			return commentVo;
		}
	}

	/**
	 * 根据userId得到评论分页
	 * 
	 * @param commentId
	 * @return pageVo
	 */
	@CacheEvict(value = "comment", allEntries = true)
	public PageVo<CommentVo> getCommentPageByUserId(long userId,
			int pageNum) {
		PageVo<CommentVo> pageVo = new PageVo<CommentVo>(pageNum);
		pageVo.setRows(20);
		pageVo.setCount(commentDao
				.getCommentCountByUserId(userId));
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", Long.toString(userId));
		pageVo.setArgs(map);
		List<CommentVo> commentlist = commentDao
				.getCommentListByUserId(
						userId,
						pageVo.getOffset(),
						pageVo.getRows());
		
		pageVo.setList(commentlist);
		return pageVo;
	}
	
	/**
	 * 根据fatherId得到评论分页
	 * @param fatherId
	 * @param pageNum
	 * @return
	 */
	@CacheEvict(value = "comment", allEntries = true)
	public PageVo<CommentVo> getCommentPageByFatherId(long fatherId,
			int pageNum) {
		PageVo<CommentVo> pageVo = new PageVo<CommentVo>(pageNum);
		pageVo.setRows(20);
		pageVo.setCount(commentDao
				.getCommentCountByFatherId(fatherId));
		Map<String, String> map = new HashMap<String, String>();
		map.put("fatherId", Long.toString(fatherId));
		pageVo.setArgs(map);
		List<CommentVo> commentlist = commentDao
				.getCommentListByFatherId(
						fatherId,
						pageVo.getOffset(),
						pageVo.getRows());
		
		pageVo.setList(commentlist);
		return pageVo;
	}
	
	
	
	/**
	 * 根据status得到评论分页
	 * 
	 * @param commentId
	 * @return pageVo
	 */
	@CacheEvict(value = "comment", allEntries = true)
	public PageVo<CommentVo> getCommentPageByStatus(CommentConstant.Status status,
			int pageNum) {
		PageVo<CommentVo> pageVo = new PageVo<CommentVo>(pageNum);
		pageVo.setRows(20);
		pageVo.setCount(commentDao
				.getCommentCountByStatus(status));
		Map<String, String> map = new HashMap<String, String>();
		map.put("status", status.toString());
		pageVo.setArgs(map);
		List<CommentVo> commentlist = commentDao
				.getCommentListByStatus(
						status,
						pageVo.getOffset(),
						pageVo.getRows());
		
		pageVo.setList(commentlist);
		return pageVo;
	}
	
	@CacheEvict(value = "comment", allEntries = true)
	public int getCommentCountByStatus(CommentConstant.Status status){
		return commentDao.getCommentCountByStatus(status);
		
	}

	
	
	

}
