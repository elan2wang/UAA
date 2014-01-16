/**
 * 
 * Copyright 2013.  All rights reserved. 
 * 
 */
package org.uaa.security.exception;

/**
 * @author wangjian
 * @create 2013年8月1日 下午11:32:05
 * @update TODO
 * 
 * 
 */
public class NullAuthenticationException extends AuthenticationException {

	private static final long serialVersionUID = 4856405037907297475L;

	public NullAuthenticationException(String msg) {
		super(msg);
	}
	
	public NullAuthenticationException(String msg, Throwable t) {
		super(msg, t);
	}
}
