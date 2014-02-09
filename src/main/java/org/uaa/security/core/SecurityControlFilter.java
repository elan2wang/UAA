package org.uaa.security.core;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.uaa.common.AppContext;
import org.uaa.security.exception.AccessDeniedException;
import org.uaa.security.exception.AuthenticationException;

/**
 * Servlet Filter implementation class SecurityControlFilter
 */
public class SecurityControlFilter implements Filter {
	private static Logger log = LoggerFactory.getLogger(SecurityControlFilter.class);
	
	private LoggerManager loggerManager;
	private AuthenticationManager authenticationManager;
	private AuthorizationManager authorizationManager;
	private ExceptionHandler exceptionHandler;
	private LoginHandler loginHandler;
	private LogoutHandler logoutHandler;

	public SecurityControlFilter() {
		loggerManager = (LoggerManager) AppContext.getBean("loggerManager");
		authenticationManager = (AuthenticationManager) AppContext.getBean("authenticationManager");
		authorizationManager = (AuthorizationManager) AppContext.getBean("authorizationManager");
		exceptionHandler = (ExceptionHandler) AppContext.getBean("exceptionHandler");
		loginHandler = (LoginHandler) AppContext.getBean("loginHandler");
		logoutHandler = (LogoutHandler) AppContext.getBean("logoutHandler");
	}

	public void init(FilterConfig fConfig) throws ServletException {
		if (fConfig.getInitParameter("login_url") != null) {
			loginHandler.setLogin_url(fConfig.getInitParameter("login_url"));
		}
		if (fConfig.getInitParameter("logout_url") != null) {
			logoutHandler.setLogout_url(fConfig.getInitParameter("logout_url"));
		}
		if (fConfig.getInitParameter("principal_name") != null) {
			loginHandler.setPrincipal_name(fConfig.getInitParameter("principal_name"));
		}
		if (fConfig.getInitParameter("credential_name") != null) {
			loginHandler.setCredential_name(fConfig.getInitParameter("credential_name"));
		}

	}

	public void destroy() { }
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpRequestResponseHolder holder = new HttpRequestResponseHolder(req, resp);
		
		UsernamePasswordToken tokenBeforeAuthenticated = null;
		try {
			loggerManager.logBefore(req);
			if (loginHandler.isLogin(req)) {
				log.debug("request login");
				tokenBeforeAuthenticated = loginHandler.handle(req);
			} else if (logoutHandler.isLogout(req)) {
				logoutHandler.handle(req, resp);
				return;
			} else {
				tokenBeforeAuthenticated = authenticationManager.getToken(req);
			}
			
			UsernamePasswordToken tokenAfterAuthenticated = authenticationManager.authenticate(tokenBeforeAuthenticated, holder);
			
			authorizationManager.decide(tokenAfterAuthenticated, req);	
			// set securityContext
			SecurityContext context = new SecurityContext();
			context.setAuthenticationToken(tokenAfterAuthenticated);
			SecurityContextHolder.addSecurityContext(context);
			// pass the request along the filter chain
			chain.doFilter(request, response);
		} catch (AuthenticationException e) {
			exceptionHandler.handle(e, req, resp);
		} catch (AccessDeniedException e1) {
			exceptionHandler.handle(e1, req, resp);
		} finally {
			loggerManager.logAfter(req);
			SecurityContextHolder.removeSecurityContext();
		}
	}
	
}
