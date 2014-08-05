package com.pivotal.example.xd;

public class RetailOrder {
	
	private int _customerId;
	private int _id;
	private double _amt;
	private int _storeId;
	
	public RetailOrder(int customerId, int orderId, double amount, int storeId) {
		_customerId = customerId;
		_id = orderId;
		_amt = amount;
		_storeId = storeId;
	}
	
	public int getCustomerId() {
		return _customerId;
	}
	public void setCustomerId(int customerId) {
		_customerId = customerId;
	}
	public int getId() {
		return _id;
	}
	public void setId(int id) {
		_id = id;
	}
	public double getAmt() {
		return _amt;
	}
	public void setAmt(float amt) {
		_amt = amt;
	}
	public int getStoreId() {
		return _storeId;
	}
	public void setStoreId(int storeId) {
		_storeId = storeId;
	}
	
}
