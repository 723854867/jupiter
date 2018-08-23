package org.jupiter.dispatcher.lane;

import java.util.concurrent.ExecutorService;

import org.jetlang.fibers.Fiber;
import org.jupiter.callback.NullCallback;

public class LoadBalanceLaneAdapter implements ILane {
	
	private Lane lane;
	private NullCallback disposeHook;
	
	LoadBalanceLaneAdapter(Lane lane) {
		this.lane = lane;
	}
	
	LoadBalanceLaneAdapter(Lane lane, NullCallback disposeHook) {
		this.lane = lane;
		this.disposeHook = disposeHook;
	}

	@Override
	public String id() {
		return lane.id();
	}

	@Override
	public Fiber fiber() {
		return lane.fiber();
	}
	
	@Override
	public void dispose() {
		this.lane = null;
		if (null != disposeHook) {
			this.disposeHook.invoke();
			this.disposeHook = null;
		}
	}

	@Override
	public ExecutorService executor() {
		return lane.executor();
	}

	@Override
	public boolean isOnSameLane(Thread thread) {
		return lane.isOnSameLane(thread);
	}
	
	@Override
	public int hashCode() {
		return lane.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		return lane.equals(obj);
	}
}
