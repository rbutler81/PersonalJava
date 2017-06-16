package com.kraken.api.json;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Response {

	private static final String ERROR = "error";
	
	@JsonProperty(ERROR)
	private List<String> error;
	
	public Response(final List<String> error){
		this.error = error;
	}
	
	public Response(){
		this.error = new ArrayList<>();
	}
	
	@JsonProperty(ERROR)
	public List<String> getError(){
		return error;
	}
	
	
	
	
	

}