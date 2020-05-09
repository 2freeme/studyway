package com.base.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;

public class MailSenderHelper {
	
	private final static Logger logger = LoggerFactory.getLogger(MailSenderHelper.class.getName());
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Resource(name="mailSender_taskExecutor")
	private TaskExecutor taskExecutor;
	
	private boolean enableMailService = false;
	
	private String from;
	
	private String[] to;
	
	private String[] bcc;
	
	private String[] cc;
	
	private String env;
	
	public void send(String subject, String text, String[] to) throws Exception{
		try{
			logger.debug("发送邮件标题： " + subject + ", 内容：" + text + ",发送到：" + to.toString());
			final MimeMessage message = mailSender.createMimeMessage(); 
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true); 
			messageHelper.setFrom(from);
			messageHelper.setTo(to);
			messageHelper.setBcc(cc);
			messageHelper.setBcc(bcc);
			messageHelper.setSubject("["+env+"]"+subject);
			messageHelper.setText(text,true);
			
			// 用新线程发送邮件，防止邮件发送超时导致DB事务超时
			taskExecutor.execute(new Runnable() {
				@Override
				public void run() {
					try {
						mailSender.send(message);
					} catch (Exception ex) {
						logger.error("邮件发送失败", ex);
						throw ex;
					}
				}
			});
			
		}catch(Exception e){
			logger.error("邮件发送失败，错误为：" + e);
			throw e;
		}
	}
	
	
	//默认是普通邮件
	public void send(String subject, String text) {
		this.send(subject, text, false);
	}
	
	//支持普通邮件和复杂邮件
	public void send(String subject, String text, boolean isMime) {
		if(! enableMailService) {
			logger.debug("邮件服务被禁用");
			return;
		}
		
		if (isMime) {
			sendMimeMail(subject, text); //复杂邮件
		} else {
			sendSimpleMail(subject, text); //普通邮件
		}		
	}
	
	
	//支持普通邮件
	public void sendSimpleMail(String subject, String text) {		
		try {
			logger.debug("发送邮件标题： " + subject + ", 内容：" + text);			
			final SimpleMailMessage message = new SimpleMailMessage();
			
			message.setFrom(from);
			message.setTo(to);
			message.setBcc(cc);
			message.setBcc(bcc);
			message.setSubject("["+env+"]"+subject);
			message.setText(text);
			
			// 用新线程发送邮件，防止邮件发送超时导致DB事务超时
			taskExecutor.execute(new Runnable() {
				@Override
				public void run() {
					try {
						mailSender.send(message);
					} catch (Exception ex) {
						logger.error("邮件发送失败", ex);
					}
				}
			});
			
		} catch (Exception ex) { // 外层捕捉不到子线程的异常
			logger.error("邮件发送失败", ex);
		}
	}
		
	//支持复杂邮件
	private void sendMimeMail(String subject, String text) {				
		try {
			logger.debug("发送邮件标题： " + subject + ", 内容：" + text);			
			final MimeMessage message = mailSender.createMimeMessage(); 
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true); //true to support attachement
			
			messageHelper.setFrom(from);
			messageHelper.setTo(to);
			messageHelper.setBcc(cc);
			messageHelper.setBcc(bcc);
			messageHelper.setSubject("["+env+"]"+subject);
			messageHelper.setText(text, true); //true to support html
			
			// 用新线程发送邮件，防止邮件发送超时导致DB事务超时
			taskExecutor.execute(new Runnable() {
				@Override
				public void run() {
					try {
						mailSender.send(message);
					} catch (Exception ex) {
						logger.error("邮件发送失败", ex);
					}
				}
			});
			
		} catch (Exception ex) { // 外层捕捉不到子线程的异常
			logger.error("邮件发送失败", ex);
		}
	}

	
	public void setFrom(String from) {
		this.from = from;
	}

	public void setTo(String[] to) {
		this.to = to;
	}

	public void setBcc(String[] bcc) {
		this.bcc = bcc;
	}

	public void setCc(String[] cc) {
		this.cc = cc;
	}

	public void setEnableMailService(boolean enableMailService) {
		this.enableMailService = enableMailService;
	}

	public String getEnv() {
		return env;
	}

	public void setEnv(String env) {
		this.env = env;
	}
	
	
}
