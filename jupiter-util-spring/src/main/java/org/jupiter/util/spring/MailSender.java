package org.jupiter.util.spring;

import java.io.File;
import java.io.InputStream;
import java.util.Set;

/**
 * 邮件发送类
 * 
 * @author lynn
 */
public interface MailSender {

	void sendMail(String acceptor, String subject, String text);
	
	void sendHtmlMail(Set<String> acceptors, String subject, String text);
	
	void sendMail(String acceptor, String subject, String text, File file);
	
	void sendMail(String acceptor, String subject, String text, String fileName, InputStream input);
}
