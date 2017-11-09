package com.cimcorp.plcUtil.almdEditor;

public class AlarmTableElement {

	private int bit;
	private String name;
	private String description;
	private String reset;
	private int time;
	private String type;
	
	public AlarmTableElement() {
		name = "";
		description = "";
		reset = "";
		type = "";
	}
	
	public AlarmTableElement(int bit, String name, String description, String reset, int time) {
		this.bit = bit;
		this.name = name;
		this.description = description;
		this.reset = reset;
		this.time = time;
	}
	
	public AlarmTableElement(String asset, String type, AlarmTableElement a) {
		
		bit = a.getBit();
		name = asset + " " + a.getName();
		
		if (a.getDescription().equals("")) {
			description = name;
		}
		else {
			description = asset + " " + a.getDescription();
		}
		
		if (!a.getReset().equals("")) {
			
			if (a.getReset().startsWith("ASSET.")) {
				reset = asset + "." + a.getReset().substring(6);
			}
			else {
				reset = a.getReset();
			}
		}
		else {
			reset = "";
		}
		
		this.type = type;
		time = a.getTime();
	}

	public int getBit() {
		return bit;
	}

	public void setBit(int bit) {
		this.bit = bit;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getReset() {
		return reset;
	}

	public void setReset(String reset) {
		this.reset = reset;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
	
}
