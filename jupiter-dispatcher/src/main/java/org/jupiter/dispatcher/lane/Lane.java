package org.jupiter.dispatcher.lane;

import java.util.concurrent.ExecutorService;

import org.jetlang.fibers.Fiber;

public class Lane implements ILane {

	private Fiber fiber;
	private String name;
	private ExecutorService exec;
	
	Lane(String name, ExecutorService exec, Fiber fiber) {
		this.name = name;
		this.exec = exec;
		this.fiber = fiber;
	}
	
	@Override
	public String id() {
		return this.name;
	}

	@Override
	public Fiber fiber() {
		return this.fiber;
	}
	
	@Override
	public ExecutorService executor() {
		return this.exec;
	}
	
	@Override
	public boolean isOnSameLane(Thread thread) {
		return thread.getName().equals(name);
	}
	
	@Override
	public int hashCode() {
		return ((null == this.name) ? 0 : this.name.hashCode());
	}
	
	@Override
	public boolean equals(Object obj) {
		if (null == obj || getClass() != obj.getClass())
			return false;
		if (this == obj)
			return true;
		Lane other = (Lane) obj;
		return null == this.name ? null == other.name : this.name.equals(other.name);
	}
	
	@Override
	public void dispose() {
		this.fiber.dispose();
		this.exec.shutdown();
		this.exec = null;
		this.fiber = null;
	}
}
