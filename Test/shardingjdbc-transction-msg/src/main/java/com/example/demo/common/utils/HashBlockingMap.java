package com.example.demo.common.utils;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class HashBlockingMap<K,V> implements BlockingMap<K,V> {

	private ConcurrentMap<K, Item<V>> map;

	private final ReentrantLock lock = new ReentrantLock();

	public HashBlockingMap() {
		map = new ConcurrentHashMap<K, Item<V>>();
	}

	public void put(K key, V o) throws InterruptedException {
		final ReentrantLock lock = this.lock;
		lock.lockInterruptibly();
		try {
			if (map.containsKey(key)) {
				Item<V> item = map.get(key);
				item.put(o);
			} else {
				Item<V> item = new Item<V>();
				map.put(key, item);
				item.put(o);
			}
		} finally {
            lock.unlock();
        }
	}

	public V take(K key) throws InterruptedException {
		final ReentrantLock lock = this.lock;
		lock.lockInterruptibly();
		try {
			if (!map.containsKey(key)) {
				map.put(key, new Item<V>());
			}
		} finally {
            lock.unlock();
        }

		Item<V> item = map.get(key);
		V x = item.take();
		map.remove(key);

		return x;
	}

	public V poll(K key, long timeout) throws InterruptedException {
		final ReentrantLock lock = this.lock;
		lock.lockInterruptibly();
		try {
			if (!map.containsKey(key)) {
				map.put(key, new Item<V>());
			}
		} finally {
            lock.unlock();
        }

		Item<V> item = map.get(key);
		V x = item.poll(timeout);
		map.remove(key);

		return x;
	}

	private static class Item<E> {

		private final ReentrantLock lock = new ReentrantLock();

		private final Condition cond = lock.newCondition();

		private E obj = null;

		private void put(E o) throws InterruptedException {
			if (o == null)
				throw new NullPointerException();
			final ReentrantLock lock = this.lock;
			lock.lockInterruptibly();
			try {
				obj = o;
				cond.signal();
			} finally {
				lock.unlock();
			}
		}

		E take() throws InterruptedException {
			E x;
			final ReentrantLock lock = this.lock;
			lock.lockInterruptibly();
			try {
				try {
					while (obj == null) {
						cond.await();
					}
				} catch (InterruptedException ie) {
					cond.signal();
					throw ie;
				}
				x = obj;
			} finally {
				lock.unlock();
			}
			return x;
		}

		private E poll(long timeout) throws InterruptedException {
			timeout = TimeUnit.MILLISECONDS.toNanos(timeout);
			E x;
			final ReentrantLock lock = this.lock;
			lock.lockInterruptibly();
			try {
	            for (;;) {
	                if (obj != null) {
	                    x = obj;
	                    break;
	                }
	                if (timeout <= 0) {
	                    return null;
	                }
	                try {
	                	timeout = cond.awaitNanos(timeout);
	                } catch (InterruptedException ie) {
	                	cond.signal();
	                    throw ie;
	                }
	            }
	        } finally {
	        	lock.unlock();
	        }
			return x;
		}

	}
	
	public static void main(String[] args) throws InterruptedException {
		// 消费者根据sequence取得自己想要的对象
		final BlockingMap<String,String> blockingMap = new HashBlockingMap<String,String>();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					String response = blockingMap.poll("123", 3000l);
					System.out.println(Thread.currentThread().getName()+"获取结果"+response);
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
		}).start();
		// 生产者
		String ojb = "sunhuo";
		blockingMap.put("1234", "sunhuo");
		System.out.println(Thread.currentThread().getName()+"已经放入对象:"+ojb);
	}

}