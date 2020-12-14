package com.trade.statemachine.web.entity;
import com.trade.statemachine.core.IPersist;
public class TradeOrderX implements IPersist {
	private String id;
	private String state;
	public boolean isGroup() {
		return isGroup;
	}
	public void setGroup(boolean group) {
		isGroup = group;
	}
	private boolean isGroup;
	public TradeOrderX() {

	}
	public TradeOrderX(String id, boolean isGroup, String state) {
		super();
		this.id = id;
		this.state=state;
		this.isGroup = isGroup;
	}
	public TradeOrderX(String id) {
		super();
		this.id = id;
	}
	@Override
	public String toString() {
		return "Order [id=" + id + ",  address=" + isGroup + "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setState(String state) {
		this.state = state;
	}
	@Override
	public String getState() {
		return state;
	}
}