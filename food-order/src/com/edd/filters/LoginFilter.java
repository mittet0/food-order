package com.edd.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.edd.food.beans.UserView;

/**
 * Filter checks if LoginBean has loginIn property set to true. If it is not set
 * then request is being redirected to the login.xhml page.
 * 
 */
public class LoginFilter implements Filter {

	/**
	 * Checks if user is logged in. If not it redirects to the login.xhtml page.
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// Get the userBean from session attribute
		UserView user = (UserView) ((HttpServletRequest) request)
				.getSession().getAttribute("userBean");
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String url = req.getRequestURI();
		
		if (user == null || !user.isLoggedIn()) {
			if (url.indexOf("/logout.xhtml") >= 0
					|| url.indexOf("/food.xhtml") >= 0) {
				res.sendRedirect(req.getContextPath() + "/logged/login.xhtml");
			} else {
				chain.doFilter(request, response);
			}
		} else {
//			if (url.indexOf("/logout.xhtml") >= 0) {
//				req.getSession().removeAttribute("userBean");
//				res.sendRedirect(req.getContextPath() + "/login.xhtml");
//			} else {
				chain.doFilter(request, response);
//			}
		}
	}

	public void init(FilterConfig config) throws ServletException {
		// Nothing to do here!
	}

	public void destroy() {
		// Nothing to do here!
	}

}