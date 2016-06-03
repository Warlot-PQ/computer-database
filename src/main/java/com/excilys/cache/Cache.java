package com.excilys.cache;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.bean.CompanyDTO;

/**
 * Cache containing : 
 * - total computers number 
 * - total companies (List of CompanyDTO flush on add/relete on DB).
 * Singleton class.
 * 
 * @author pqwarlot
 *
 */
public class Cache {
	private static Logger LOGGER = null;
	private static Cache INSTANCE = new Cache();
	private static final ConcurrentMap<String, Object> MAP = new ConcurrentHashMap<>();
	private static final String COUNT_COMPUER = "countComputer";
	private static final String COMPANIES = "companies";

	private Cache() {
		LOGGER = LoggerFactory.getLogger(this.getClass());
	}

	public static Cache getInstance() {
		return INSTANCE;
	}

	public Integer getCountComputer() {
		Integer countComputer = (Integer) MAP.get(COUNT_COMPUER);
		LOGGER.debug("Count cached value read: " + countComputer);
		return countComputer;
	}

	public void setCountComputer(Integer countComputer) {
		MAP.put(COUNT_COMPUER, countComputer);
		LOGGER.debug("Count cached value setted: " + countComputer);
	}

	/**
	 * Incremente cound cached value by valueToAdd
	 * 
	 * @param valueToAdd
	 *            int
	 */
	public void incrementCount(int valueToAdd) {
		if (MAP.get(COUNT_COMPUER) != null) {
			setCountComputer((Integer) MAP.get(COUNT_COMPUER) + valueToAdd);
		}
	}

	/**
	 * Decremente cound cached value by valueToSup
	 * 
	 * @param valueToSup
	 *            int
	 */
	public void decrementCount(int valueToSup) {
		if (MAP.get(COUNT_COMPUER) != null) {
			setCountComputer((Integer) MAP.get(COUNT_COMPUER) - valueToSup);
		}
	}

	public List<CompanyDTO> getCompanies() {
		@SuppressWarnings("unchecked")
		List<CompanyDTO> companies = (List<CompanyDTO>) MAP.get(COMPANIES);
		LOGGER.debug("Company cached value read: " + companies);
		return companies;
	}

	public void setCompanies(List<CompanyDTO> companies) {
		MAP.put(COMPANIES, companies);
		LOGGER.debug("Company cached value setted: " + companies);
	}

	public void flushCompanies() {
		MAP.remove(COMPANIES);
	}
}
