package org.jupiter.test.redis;

import javax.annotation.Resource;

import org.junit.Test;
import org.jupiter.redis.ops.LuaOps;
import org.jupiter.test.JupiterTest;

public class LuaOpsTest extends JupiterTest {

	@Resource
	private LuaOps luaOps;
	
	@Test
	public void testCaptchaObtain() {
		long flag = luaOps.captchaObtain("captcha", "captcha:count", "1234", 300000, 10, 86400000, 60000);
		System.out.println(flag);
	}
	
	@Test
	public void testCaptchaVerify() { 
//		long flag = luaOps.captchaObtain("captcha:1", "captcha:count:1", "1234", 300000, 10, 86400000, 60000);
//		System.out.println(flag);
//		flag = luaOps.captchaObtain("captcha:2", "captcha:count:2", "5678", 300000, 10, 86400000, 60000);
//		System.out.println(flag);
		System.out.println(luaOps.captchaVerify("captcha:1", "captcha:2", "1234", "5678"));
	}
}
