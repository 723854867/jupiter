package org.jupiter.dispatcher;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PrintRejectedExecutionHandler implements RejectedExecutionHandler {
	
	@Override
	public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
		log.warn("task rejected, this may be an unexcpected problem.");
	}
}
