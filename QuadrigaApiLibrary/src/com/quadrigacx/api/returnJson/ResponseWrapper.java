package com.quadrigacx.api.returnJson;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.quadrigacx.api.returnJson.deserializer.ResponseWrapperDeserializer;

@JsonDeserialize(using=ResponseWrapperDeserializer.class)
public class ResponseWrapper {
	
	private ErrorResponse errorResponse;
	private TickerResponse tickerResponse;
	private OrderBookResponse orderBookResponse;
	private TransactionsResponse transactionsResponse;
	private BalanceResponse balanceResponse;
	private UserTransactionsResponse userTransactionsResponse;
	private OpenOrdersResponse openOrdersResponse;
	private OrderLookupResponse orderLookupResponse;
	private OrderResponse orderResponse;
	private boolean orderCanceled;
	
	public ResponseWrapper(){
		errorResponse = new ErrorResponse();
		tickerResponse = new TickerResponse();
		orderBookResponse = new OrderBookResponse();
		transactionsResponse = new TransactionsResponse();
		balanceResponse = new BalanceResponse();
		userTransactionsResponse = new UserTransactionsResponse();
		openOrdersResponse = new OpenOrdersResponse();
		orderLookupResponse = new OrderLookupResponse();
		orderResponse = new OrderResponse();
		orderCanceled = false;
	}

	public OrderResponse getLimitOrderResponse() {
		return orderResponse;
	}

	public void setOrderResponse(OrderResponse limitOrderResponse) {
		this.orderResponse = limitOrderResponse;
	}

	public boolean isOrderCanceled() {
		boolean rVal = this.orderCanceled;
		this.orderCanceled = false;
		return rVal;
	}

	public void setOrderCanceled(boolean orderCanceled) {
		this.orderCanceled = orderCanceled;
	}

	public OrderLookupResponse getOrderLookupResponse() {
		return orderLookupResponse;
	}

	public void setOrderLookupResponse(OrderLookupResponse orderLookupResponse) {
		this.orderLookupResponse = orderLookupResponse;
	}

	public OpenOrdersResponse getOpenOrdersResponse() {
		return openOrdersResponse;
	}

	public void setOpenOrdersResponse(OpenOrdersResponse openOrdersResponse) {
		this.openOrdersResponse = openOrdersResponse;
	}

	public ErrorResponse getErrorResponse() {
		return errorResponse;
	}

	public void setErrorResponse(ErrorResponse errorResponse) {
		this.errorResponse = errorResponse;
	}

	public TickerResponse getTickerResponse() {
		return tickerResponse;
	}

	public void setTickerResponse(TickerResponse tickerResponse) {
		this.tickerResponse = tickerResponse;
	}

	public OrderBookResponse getOrderBookResponse() {
		return orderBookResponse;
	}

	public void setOrderBookResponse(OrderBookResponse orderBookResponse) {
		this.orderBookResponse = orderBookResponse;
	}

	public TransactionsResponse getTransactionsResponse() {
		return transactionsResponse;
	}

	public void setTransactionsResponse(TransactionsResponse transactionsResponse) {
		this.transactionsResponse = transactionsResponse;
	}

	public BalanceResponse getBalanceResponse() {
		return balanceResponse;
	}

	public void setBalanceResponse(BalanceResponse balanceResponse) {
		this.balanceResponse = balanceResponse;
	}

	public UserTransactionsResponse getUserTransactionsResponse() {
		return userTransactionsResponse;
	}

	public void setUserTransactionsResponse(UserTransactionsResponse userTransactionsResponse) {
		this.userTransactionsResponse = userTransactionsResponse;
	}
	
	

	
}
