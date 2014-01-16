/**
 * 
 * Copyright 2013.  All rights reserved. 
 * 
 */
package org.uaa.security.exception;

/**
 * @author wangjian
 * @create 2013年8月1日 下午7:54:10
 * @update TODO
 * 
 * 
 */
public class BadCredentialsException extends AuthenticationException {
	
	private static final long serialVersionUID = -4809459829204836467L;

	public BadCredentialsException(String msg) {
		super(msg);
	}
	
	public BadCredentialsException(String msg, Throwable t) {
		super(msg, t);
	}

}
