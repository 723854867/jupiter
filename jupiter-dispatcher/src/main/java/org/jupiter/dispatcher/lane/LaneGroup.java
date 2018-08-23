package org.jupiter.dispatcher.lane;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.jetlang.core.BatchExecutor;
import org.jetlang.core.BatchExecutorImpl;
import org.jetlang.fibers.Fiber;
import org.jetlang.fibers.PoolFiberFactory;
import org.jupiter.callback.NullCallback;
import org.jupiter.util.concurrent.NamedThreadFactory;
import org.springframework.util.Assert;

import lombok.Setter;

public class LaneGroup {

	@Setter
	private int laneNum;
	@Setter
	private String name;
	private int[] facts;
	private Lane[] lanes;
	private boolean initialized;
	@Setter
	private BatchExecutor batchExecutor = new BatchExecutorImpl();
	
	public LaneGroup(String name, int laneNum) {
		Assert.hasText(name, "lane group has no name");
		Assert.isTrue(laneNum >= 1, "lane group size must large than 1");
		this.name = name;
		this.laneNum = laneNum;
	}
	
	public void init() { 
		if (initialized)
			return;
		synchronized (this) {
			if (initialized)
				return;
			this.initialized = true;
			this.facts = new int[this.laneNum];
			this.lanes = new Lane[this.laneNum];
			ThreadFactory threadFactory = new NamedThreadFactory(name, true);

			for (int i = 1; i <= this.laneNum; i++) {
				ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), threadFactory);
				executor.prestartAllCoreThreads();
				Fiber fiber = _createFiber(executor);
				Lane lane = new Lane(this.name + "[" + i + "]", executor, fiber);
				this.lanes[i - 1] = lane;
			}
		}
	}
	
	private Fiber _createFiber(ThreadPoolExecutor executor) {
		PoolFiberFactory factory = new PoolFiberFactory(executor);
		Fiber fiber = factory.create(this.batchExecutor);
		fiber.start();
		return fiber;
	}
	
	public ILane chooseLane() {
		Assert.isTrue(initialized, "lane group hasn't initialized not yet!");
		// if length is 1, return the unique lane immediately
		if (laneNum == 1) 										
			return new LoadBalanceLaneAdapter(lanes[0]);
		
		// if lane number large than 1, should record the fact of each lane in the loadBalanceMap
		int index = 0;
		int fact = facts[index];
		Lane lane = lanes[index];
		synchronized (this) {
			for (int i = 1; i < laneNum; i++) {
				if (facts[i] >= fact)
					continue;
				index = i;
				fact = facts[index];
				lane = lanes[index];
			}
			facts[index]++;
		}
		
		final int findex = index;
		LoadBalanceLaneAdapter laneProxy = new LoadBalanceLaneAdapter(lane, new NullCallback() {
			@Override
			public void invoke() {
				synchronized (LaneGroup.this) {
					facts[findex]--;
				}
			}
		});
		return laneProxy;
	}
	
	public void dispose() { 
		synchronized (this) {
			this.initialized = false;
			for (Lane lane : lanes)
				lane.dispose();
			this.lanes = null;
			this.facts = null;
		}
	}
}
