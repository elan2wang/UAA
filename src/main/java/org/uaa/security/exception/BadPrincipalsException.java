/**
 * 
 * Copyright 2013.  All rights reserved. 
 * 
 */
package org.uaa.security.exception;

/**
 * @author wangjian
 * @create 2013年8月1日 下午7:43:21
 * @update TODO
 * 
 * 
 */
public class BadPrincipalsException extends AuthenticationException {

	private static final long serialVersionUID = 3004003593460021708L;

	public BadPrincipalsException(String msg) {
		super(msg);
	}
	
	public BadPrincipalsException(String msg, Throwable t) {
		super(msg, t);
	}

}
