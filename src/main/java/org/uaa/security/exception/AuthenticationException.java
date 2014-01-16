/**
 * 
 * Copyright 2013.  All rights reserved. 
 * 
 */
package org.uaa.security.exception;

/**
 * @author wangjian
 * @create 2013年8月1日 下午7:46:12
 * @update TODO
 * 
 * 
 */
public class AuthenticationException extends RuntimeException {

	private static final long serialVersionUID = 5870855616395222379L;

	public AuthenticationException(String msg) {
		super(msg);
	}
	
	public AuthenticationException(String msg, Throwable t) {
		super(msg, t);
	}
	
}
