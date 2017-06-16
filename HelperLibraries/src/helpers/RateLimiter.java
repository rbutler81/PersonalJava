package helpers;

import java.util.concurrent.locks.ReentrantLock;

public class RateLimiter {

	private ReentrantLock lock;
	private long minTime;
	private long lastTime;
	
	public RateLimiter(long minTime){
		
		lock = new ReentrantLock(true);
		this.minTime = minTime;
		lastTime = System.currentTimeMillis() - minTime;
	}
	
	public boolean waitTurn(){
		
		lock.lock();
		try{
			while (lastTime + minTime >= System.currentTimeMillis()){}
			lastTime = System.currentTimeMillis();
		}
		finally{lock.unlock();}
		
		return true;
	}
	
	public boolean isLocked(){
		return lock.isLocked();
	}
	
}
