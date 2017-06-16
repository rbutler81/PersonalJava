package com.quadrigacx.api.returnJson;

public class ErrorResponse {

	private int errCode = 0;
	private String errMessage = "";
	
	public ErrorResponse(){
		this.errCode = 0;
		this.errMessage = "";
	}
	
	public ErrorResponse(int errCode, String errMessage){
		this.errCode = errCode;
		this.errMessage = errMessage;
	}

	public int getErrCode() {
		return errCode;
	}

	public void setErrCode(int errCode) {
		this.errCode = errCode;
	}

	public String getErrMessage() {
		return errMessage;
	}

	public void setErrMessage(String errMessage) {
		this.errMessage = errMessage;
	}
	
	public boolean hasError(){
		if (errCode == 0) return false;
		else return true;
	}
	
}
