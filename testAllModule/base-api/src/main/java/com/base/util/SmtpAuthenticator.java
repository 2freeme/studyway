package com.base.util;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

// 覆盖原认证实现，实现email的用户认证
public class SmtpAuthenticator extends Authenticator {
	
	private String username;
	
    private String password;
    
    public SmtpAuthenticator(String username, String password) {
        super();
        this.username = username;
        this.password = password;
    }

    public PasswordAuthentication getPasswordAuthentication() {
    	return new PasswordAuthentication(username, password);
    }

}
