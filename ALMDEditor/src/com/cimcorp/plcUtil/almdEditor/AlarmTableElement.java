package com.cimcorp.plcUtil.almdEditor;

public class AlarmTableElement {

	private int bit;
	private String name;
	private String description;
	private String reset;
	private int time;
	private String type;
	private String asset;
	private String assocTag1;

	public AlarmTableElement() {
		name = "";
		description = "";
		reset = "";
		type = "";
		asset = "";
		assocTag1 = "";
	}

	public AlarmTableElement(int bit, String name, String description, String reset, int time, String assocTag1) {
		this.bit = bit;
		this.name = name;
		this.description = description;
		this.reset = reset;
		this.time = time;
		this.assocTag1 = assocTag1;
	}

	public AlarmTableElement(String asset, String type, AlarmTableElement a) {

		bit = a.getBit();
		name = a.getName().replace("<A>", asset);
		this.asset = asset;

		if (a.getDescription().equals("")) {
			description = name;
		} else {
			description = a.getDescription().replace("<A>", asset);
		}

		this.reset = a.getReset().replace("<A>", asset);
		this.assocTag1 = a.getAssocTag1().replace("<A>", asset);

		this.type = type;
		time = a.getTime();
	}

	public String getAssocTag1() {
		return assocTag1;
	}

	public void setAssocTag1(String assocTag1) {
		this.assocTag1 = assocTag1;
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

	public String getAsset() {
		return asset;
	}

	public void setAsset(String asset) {
		this.asset = asset;
	}

}
