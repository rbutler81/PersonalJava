package com.quadrigacx.exchange.threads;

import java.util.concurrent.locks.ReentrantLock;

	
public class ThreadControl {
	
	private ReentrantLock lock = new ReentrantLock(true);
	
	private boolean paused = false;
	private boolean done = false;
	
	public ReentrantLock getLock(){
		return this.lock;
	}
	
	public void lock(){
		this.lock.lock();
	}
	
	public void unlock(){
		this.lock.unlock();
	}
	
	public boolean isDone(){
		lock.lock();
		boolean rVal;
		rVal = this.done;
		lock.unlock();
		return rVal;
	}
	
	public void done(){
		lock.lock();
		this.done = true;
		lock.unlock();
	}
	
	public boolean isPaused(){
		lock.lock();
		boolean rVal;
		rVal = this.paused;
		lock.unlock();
		return rVal;
	}
	
	public void pause(){
		lock.lock();
		this.paused = true;
		lock.unlock();
	}
	
	public void resume(){
		lock.lock();
		this.paused = false;
		lock.unlock();
	}
}
