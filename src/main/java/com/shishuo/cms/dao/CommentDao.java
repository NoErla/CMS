/*
 *	Copyright © 2013 Changsha Shishuo Network Technology Co., Ltd. All rights reserved.
 *	长沙市师说网络科技有限公司 版权所有
 *	http://www.shishuo.com
 */

package com.shishuo.cms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.shishuo.cms.constant.CommentConstant;
import com.shishuo.cms.entity.Comment;
import com.shishuo.cms.entity.vo.CommentVo;
import com.shishuo.cms.service.CommentService;

/**
 * 评论
 * 
 * @author Harbored
 * 
 */
@Repository
public interface CommentDao {

	// ///////////////////////////////
	// ///// 增加 ////////
	// ///////////////////////////////

	/**
	 * 增加评论
	 * 
	 * @return Integer
	 */
	public int addComment(Comment comment);

	// ///////////////////////////////
	// ///// 刪除 ////////
	// ///////////////////////////////

	/**
	 * 删除评论
	 * 
	 * @return boolean
	 */
	public boolean deleteCommentById(@Param("commentId") long commentId);
	

	// ///////////////////////////////
	// ///// 修改 ////////
	// ///////////////////////////////

	/**
	 * 修改审核状态
	 * @param articleId
	 * @param check
	 * @return
	 */
	public int updateStatus(@Param("commentId") long commentId,
			@Param("status") CommentConstant.Status status);
	
	/**
	 * 修改评论信息
	 * @param commentId
	 * @param name
	 * @param userId
	 * @param content
	 * @param url
	 * @param ip
	 */
	public void updateCommentByCommentId(@Param("commentId") long commentId,
			@Param("name") String name,@Param("userId") long userId,
			@Param("content") String content,@Param("url") String url,
			@Param("ip") String ip);
	// ///////////////////////////////
	// ///// 查詢 ////////
	// ///////////////////////////////

	/**
	 * 得到评论
	 * 
	 * @param commentId
	 * @return CommentVo
	 */
	public CommentVo getCommentById(@Param("commentId") long commentId);

	/**
	 * 根据fatherId得到评论列表
	 * @param fatherId
	 * @return
	 */
	public List<CommentVo> getCommentListByFatherId(@Param("fatherId") long fatherId,@Param("offset")int offset, @Param("rows")int rows);
	
	/**
	 * 根据fatherId得到评论数目
	 * @param fatherId
	 * @return
	 */
	public int getCommentCountByFatherId(@Param("fatherId") long fatherId);
	
	/**
	 * 根据userId得到评论列表
	 * @param userId
	 * @return
	 */
	public List<CommentVo> getCommentListByUserId(@Param("userId") long userId,@Param("offset")int offset, @Param("rows")int rows);
	
	/**
	 * 根据userId得到评论的数目
	 * @param userId
	 * @return
	 */
	public int getCommentCountByUserId(@Param("userId") long userId);
	
	/**
	 * 根据status得到评论列表
	 * @param status
	 * @return
	 */
	public List<CommentVo> getCommentListByStatus(@Param("status") CommentConstant.Status status,@Param("offset")int offset, @Param("rows")int rows);
	
	/**
	 * 根据status得到评论数目
	 * @param status
	 * @return
	 */
	public int getCommentCountByStatus(@Param("status") CommentConstant.Status status);

	
	

}
