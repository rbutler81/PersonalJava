package com.quadrigacx.exchange;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quadrigacx.api.QuadrigaApi;
import com.quadrigacx.api.returnJson.ResponseWrapper;
import com.quadrigacx.exchange.threads.ThreadControl;
import com.quadrigacx.api.QuadrigaMethods;

import helpers.KeyValuePair;
import helpers.RateLimiter;

public class QuadrigaCall {

	private ObjectMapper mapper = new ObjectMapper();
	private QuadrigaApi q = new QuadrigaApi();
	private RateLimiter rl;
	private boolean isLimited = false;
	private boolean hasData = false;
	private boolean forceRefresh = false;
		
	protected ResponseWrapper r;
	protected QuadrigaMethods qm;
	protected List<KeyValuePair> kv = new ArrayList<KeyValuePair>();
	protected ThreadControl tc = new ThreadControl();
		
	public QuadrigaCall(){
		
	}
	
	public QuadrigaCall(RateLimiter rl){
		this.rl = rl;
		this.isLimited = true;
	}
	
	public ThreadControl getTc() {
		return tc;
	}

	public void setTc(ThreadControl tc) {
		this.tc = tc;
	}

	public boolean refreshData(){
		return sendApiCall();
	}
	
	public boolean refreshDataNow(){
		forceRefresh = true;
		boolean rVal = sendApiCall();
		forceRefresh = false;
		return rVal;
	}

	private boolean sendApiCall(){
	
		InjectableValues iv = new InjectableValues.Std().addValue("context", qm.getId());
		boolean success = false;
		ResponseWrapper oldR = r;
		
		while (!success){
			String result = "";
			try {
				if (isLimited && !forceRefresh) rl.waitTurn();
			
				if (kv.size() > 0){
					tc.lock();
					r = null;
					result = q.call(qm, kv);
					r = mapper.setInjectableValues(iv).readValue(result, ResponseWrapper.class);
					tc.unlock();
				}
				else{
					tc.lock();
					r = null;
					result = q.call(qm);
					r = mapper.setInjectableValues(iv).readValue(result, ResponseWrapper.class);
					tc.unlock();
				}
			
			} catch (Exception e) {
				System.out.println(Thread.currentThread().getName() + " Response returned string of " + result.length() + " : " + result);
				hasData = false;
				r = oldR;
				oldR = null;
				return false;
			}
			
			if (r.getErrorResponse().getErrCode() == 0){
				success = true;
			} 
			else if (r.getErrorResponse().getErrCode() != 200){
				success = true;
				System.out.println(Thread.currentThread().getName() + " errCode: " + r.getErrorResponse().getErrCode() + " : " + 
						r.getErrorResponse().getErrMessage());
			}
			else {
				try {
					System.out.println(Thread.currentThread().getName() + " : Too many calls, trying again in 5 seconds...");
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		if (r.getErrorResponse().getErrCode() != 0){
			tc.lock();
			hasData = false;
			tc.unlock();
			return false;
		}
		else{
			tc.lock();
			hasData = true;
			tc.unlock();
			return true;
		}
	}

	public boolean hasData() {
		return hasData;
	}

}
