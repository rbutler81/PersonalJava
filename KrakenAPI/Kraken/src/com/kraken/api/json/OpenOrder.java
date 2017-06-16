package com.kraken.api.json;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using=OpenOrderDeserializer.class)
public class OpenOrder {

	private String refId;
	private String userRef;
	private String status;
	private Double openTm;
	private Double startTm;
	private Double expireTm;
	private OpenOrderDescription descr;
	private String vol;
	private String vol_exec;
	private String cost;
	private String fee;
	private String price;
	
	public OpenOrder() {
		this.refId = "";
		this.userRef = "";
		this.status = "";
		this.openTm = 0.0;
		this.startTm = 0.0;
		this.expireTm = 0.0;
		this.descr = new OpenOrderDescription();
		this.vol = "";
		this.vol_exec = "";
		this.cost = "";
		this.fee = "";
		this.price = "";
	}
	
	public OpenOrder(String refId, String userRef, String status, Double openTm, Double startTm, Double expireTm,
			OpenOrderDescription descr, String vol, String vol_exec, String cost, String fee, String price) {
		this.refId = refId;
		this.userRef = userRef;
		this.status = status;
		this.openTm = openTm;
		this.startTm = startTm;
		this.expireTm = expireTm;
		this.descr = descr;
		this.vol = vol;
		this.vol_exec = vol_exec;
		this.cost = cost;
		this.fee = fee;
		this.price = price;
	}

	public String getRefId() {
		return refId;
	}

	public void setRefId(String RefId) {
		this.refId = RefId;
	}

	public String getUserRef() {
		return userRef;
	}

	public void setUserRef(String userRef) {
		this.userRef = userRef;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Double getOpenTm() {
		return openTm;
	}

	public void setOpenTm(Double openTm) {
		this.openTm = openTm;
	}

	public Double getStartTm() {
		return startTm;
	}

	public void setStartTm(Double startTm) {
		this.startTm = startTm;
	}

	public Double getExpireTm() {
		return expireTm;
	}

	public void setExpireTm(Double expireTm) {
		this.expireTm = expireTm;
	}

	public OpenOrderDescription getDescr() {
		return descr;
	}

	public void setDescr(OpenOrderDescription descr) {
		this.descr = descr;
	}

	public String getVol() {
		return vol;
	}

	public void setVol(String vol) {
		this.vol = vol;
	}

	public String getVol_exec() {
		return vol_exec;
	}

	public void setVol_exec(String vol_exec) {
		this.vol_exec = vol_exec;
	}

	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
	
	
	
	
	
}
