package helpers;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Timer {
	
	private double startTime;
	private double interval;


	public String getAccumTime(){
		
		if (this.startTime == 0.0) return "Timer has not started";
		else{
			BigDecimal accumTime = new BigDecimal(System.currentTimeMillis()).setScale(0, RoundingMode.DOWN);
			BigDecimal startTime = new BigDecimal((long) this.startTime).setScale(0, RoundingMode.DOWN);
			
			accumTime = accumTime.subtract(startTime);
			
			return Time.convertMilliToHMS(Long.parseLong(accumTime.toString()));
		}
	}
	
	public Timer(double interval){
		this.interval = interval;
		startTime = 0.0;
	}
	
	public void start(){
		startTime = System.currentTimeMillis();
	}
	
	public void stop(){
		startTime = 0.0;
	}
	
	public boolean isDone(){
		if ((System.currentTimeMillis() >= startTime + interval) && (startTime != 0.0)){
			return true;
		}
		else return false;
	}
	
	public boolean isStarted(){
		if (startTime != 0.0){
			return true;
		}
		else return false;
	}

}