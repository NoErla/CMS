/*
 *	Copyright © 2013 Changsha Shishuo Network Technology Co., Ltd. All rights reserved.
 *	长沙市师说网络科技有限公司 版权所有
 *	http://www.shishuo.com
 */

package com.shishuo.cms.exception;

/**
 * 
 * 评论不存在
 * 
 * @author myc
 * 
 */
public class CommentNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CommentNotFoundException(String msg) {
		super(msg);
	}
}
