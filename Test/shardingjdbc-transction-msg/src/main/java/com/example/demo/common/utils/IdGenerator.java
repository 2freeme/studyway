package com.example.demo.common.utils;

public class IdGenerator {
	private static SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
	
	public static long genId() {
		return idWorker.nextId();
	}
	
}
