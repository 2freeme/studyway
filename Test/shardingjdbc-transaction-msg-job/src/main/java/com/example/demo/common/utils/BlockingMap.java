package com.example.demo.common.utils;
public interface BlockingMap<K,V> {

	public void put(K key, V o) throws InterruptedException;

	public V take(K key) throws InterruptedException;

	public V poll(K key, long timeout) throws InterruptedException;

}