package com.kraken.api.json;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Asset {
	
	private static final String ACLASS = "aclass";
	private static final String ALTNAME = "altname";
	private static final String DECIMALS = "decimals";
	private static final String DISPLAY_DECIMALS = "display_decimals";
	
	private String name;
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
	
	@JsonProperty(ACLASS) 
	private String aClass;
	
	@JsonProperty(ALTNAME)
	private String altName;
	
	@JsonProperty(DECIMALS)
	private int decimals;
	
	@JsonProperty(DISPLAY_DECIMALS)
	private int displayDecimals;

	@JsonProperty(ACLASS)
	public String getaClass() {
		return aClass;
	}

	@JsonProperty(ACLASS)
	public void setaClass(String aClass) {
		this.aClass = aClass;
	}

	@JsonProperty(ALTNAME)
	public String getAltName() {
		return altName;
	}

	@JsonProperty(ALTNAME)
	public void setAltName(String altName) {
		this.altName = altName;
	}

	@JsonProperty(DECIMALS)
	public int getDecimals() {
		return decimals;
	}

	@JsonProperty(DECIMALS)
	public void setDecimals(int decimals) {
		this.decimals = decimals;
	}

	@JsonProperty(DISPLAY_DECIMALS)
	public int getDisplayDecimals() {
		return displayDecimals;
	}

	@JsonProperty(DISPLAY_DECIMALS)
	public void setDisplayDecimals(int displayDecimals) {
		this.displayDecimals = displayDecimals;
	}
	
	
	
}
