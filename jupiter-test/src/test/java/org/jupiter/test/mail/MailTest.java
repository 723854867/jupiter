package org.jupiter.test.mail;

import javax.annotation.Resource;

import org.junit.Test;
import org.jupiter.test.JupiterTest;
import org.jupiter.util.spring.MailSender;

public class MailTest extends JupiterTest {

	@Resource
	private MailSender mailSender;
	
	@Test
	public void testSend() { 
		mailSender.sendMail("723854867@qq.com", "sdsd", "hello");
	}
}
