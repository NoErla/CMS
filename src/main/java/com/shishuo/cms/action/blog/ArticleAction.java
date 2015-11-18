/*
 *	Copyright © 2013 Changsha Shishuo Network Technology Co., Ltd. All rights reserved.
 *	长沙市师说网络科技有限公司 版权所有
 *	http://www.shishuo.com
 */

package com.shishuo.cms.action.blog;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.shishuo.cms.entity.Folder;
import com.shishuo.cms.entity.vo.ArticleVo;
import com.shishuo.cms.entity.vo.CommentVo;
import com.shishuo.cms.entity.vo.PageVo;

/**
 * @author Herbert
 * 
 */
@Controller
@RequestMapping("/blog/article")
public class ArticleAction extends BaseAction {
	@RequestMapping(value = "/article2.htm", method = RequestMethod.POST)
	public String article2(HttpServletRequest request, ModelMap modelMap) {
		return "/template/blog/article2";
	}

	@RequestMapping(value = "/{articleId}.htm", method = RequestMethod.GET)
	public String article(@PathVariable long articleId,
			@RequestParam(value = "p", defaultValue = "1") int pageNum,
			ModelMap modelMap) {
		try {
			ArticleVo article = fileService.getArticleById(articleId);
			PageVo<CommentVo> commentList = commentService
					.getCommentPageByFatherId(articleId, pageNum);
			Folder folder = folderService.getFolderById(article.getFolderId());
			modelMap.addAttribute("p", pageNum);
			modelMap.addAttribute("folder", folder);
			modelMap.addAttribute("article", article);
			modelMap.addAttribute("commentList", commentList);
			modelMap.addAttribute("g_folderId",
					folderService.firstFolderId(folder.getFolderId()));
			return themeService.getArticleTemplate(article.getFolderId(),
					articleId);
		} catch (Exception e) {
			modelMap.addAttribute("g_folderId", 0);
			return themeService.get404();
		}
	}
}
