package com.shishuo.cms.filter;

import com.shishuo.cms.constant.SystemConstant;
import com.shishuo.cms.entity.User;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * project_name:shishuocms package_name:com.shishuo.cms.filter user: youzipi
 * date: 15-11-18 下午6:52
 */
public class LoginFilter implements Filter {

	protected final Logger logger = Logger.getLogger(this.getClass());

	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		User user = (User) request.getSession().getAttribute(
				SystemConstant.SESSION_USER);
		if (user == null) {
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path;
			response.sendRedirect(basePath + "/user/login.htm");
		} else {
			chain.doFilter(request, response);
		}
	}

	public void destroy() {
	}
}
