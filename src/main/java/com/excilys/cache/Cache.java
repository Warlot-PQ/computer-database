package com.excilys.cache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Cache 
 * @author pqwarlot
 *
 */
public class Cache {
	private static Logger LOGGER = null;
	private static Cache INSTANCE = new Cache();
	private static ConcurrentMap<String, Object> MAP = new ConcurrentHashMap<>();
	private static final String COUNT = "count"; 
	
	private Cache() {	
		LOGGER = LoggerFactory.getLogger(this.getClass());	
	}
	
	public static Cache getInstance(){
		return INSTANCE;
	}
	
	public Integer getCount() {
		Integer count = (Integer) MAP.get(COUNT);	
		LOGGER.debug("Count cached value read: " + count);
		return count;
	}
	
	public void setCount(Integer count) {
		MAP.put(COUNT, count);
		LOGGER.debug("Count cached value setted: " + count);
	}
	
	/**
	 * Incremente cound cached value by valueToAdd
	 * @param valueToAdd int
	 */
	public void incrementCount(int valueToAdd) {
		if (MAP.get(COUNT) != null) {
			setCount((Integer) MAP.get(COUNT) + valueToAdd);
		}
	}

	/**
	 * Decremente cound cached value by valueToSup
	 * @param valueToSup int
	 */
	public void decrementCount(int valueToSup) {
		if (MAP.get(COUNT) != null) {
			setCount((Integer) MAP.get(COUNT) - valueToSup);
		}
	}
}
