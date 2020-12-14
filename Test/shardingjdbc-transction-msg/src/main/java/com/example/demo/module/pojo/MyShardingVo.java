package com.example.demo.module.pojo;

public class MyShardingVo  implements  Comparable{
	private String shardingColumn;
	private Integer shardingColumnValue;
	private  Integer hintShardingValue;
	
	public MyShardingVo(String shardingColumn, Integer shardingColumnValue, Integer hintShardingValue) {
		super();
		this.shardingColumn = shardingColumn;
		this.shardingColumnValue = shardingColumnValue;
		this.hintShardingValue = hintShardingValue;
	}
	public String getShardingColumn() {
		return shardingColumn;
	}
	public void setShardingColumn(String shardingColumn) {
		this.shardingColumn = shardingColumn;
	}
	public Integer getShardingColumnValue() {
		return shardingColumnValue;
	}
	public void setShardingColumnValue(Integer shardingColumnValue) {
		this.shardingColumnValue = shardingColumnValue;
	}
	public Integer getHintShardingValue() {
		return hintShardingValue;
	}
	public void setHintShardingValue(Integer hintShardingValue) {
		this.hintShardingValue = hintShardingValue;
	}
	@Override
	public int compareTo(Object arg0) {
		return 0;
	}
}
