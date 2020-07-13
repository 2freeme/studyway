package com.example.redis.rpcutil.exception;

/**
 * User: zhaolp
 * Date: 17-6-29 下午5:59
 */
@SuppressWarnings("serial")
public class SendRefuseException extends Exception {
	public SendRefuseException() {
		super();
	}

	public SendRefuseException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public SendRefuseException(String arg0) {
		super(arg0);
	}

	public SendRefuseException(Throwable arg0) {
		super(arg0);
	}

}
