package com.example.demo.module.pojo;

public enum TimeUnitEnum {// //#日誌保留時間单位：m（一個月）1d(一天)  1h(一小時)  1M(一分鐘)  1s(1秒)
	MON("m", 1), DAY("d", 2), HOUR("h", 3), MIN("M", 4), SEC("s", 4);
	// 成员变量
	private String name;
	private int index;
	// 构造方法
	private TimeUnitEnum(String name, int index) {
		this.name = name;
		this.index = index;
	}
	// 普通方法
	public static String getName(int index) {
		for (TimeUnitEnum c : TimeUnitEnum.values()) {
			if (c.getIndex() == index) {
				return c.name;
			}
		}
		return null;
	}
	// get set 方法
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
}