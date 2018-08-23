package org.jupiter.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ComponentScan("org.jupiter")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = JupiterTest.class)
public class JupiterTest {

	@Test
	public void test() {}
}
