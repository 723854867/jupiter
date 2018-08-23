package org.jupiter.dispatcher.lane;

import java.util.concurrent.ExecutorService;

import org.jetlang.fibers.Fiber;

public interface ILane {

	String id();
	
	Fiber fiber();
	
	void dispose();
	
	ExecutorService executor();
	
	boolean isOnSameLane(Thread thread);
}
