/**
 * 
 * Copyright 2013.  All rights reserved. 
 * 
 */
package org.uaa.security.exception;

/**
 * @author wangjian
 * @create 2013年8月1日 下午7:58:57
 * @update TODO
 * 
 * 
 */
public class AccessDeniedException extends RuntimeException {

	private static final long serialVersionUID = 2115629397377757932L;

	public AccessDeniedException(String msg) {
		super(msg);
	}
	
	public AccessDeniedException(String msg, Throwable t) {
		super(msg, t);
	}

}
