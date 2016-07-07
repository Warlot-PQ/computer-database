package com.excilys.persistence.repository.impl;

import java.util.HashMap;
import java.util.Map;

import com.excilys.persistence.pagination.PageRequest;

/**
 * Create a JPQL query
 * The count option disable LIMIT, OFFSET and ORDER BY options.
 * @author pqwarlot
 *
 */
public class QueryCreator {
	private PageRequest pageRequest;
	private boolean isCount;
	private static Map<String, String> tableFieldConverter = new HashMap<>();
	private static Map<Boolean, String> tableOrderConverter = new HashMap<>();
	
	static {
		tableFieldConverter.put("name", "computer.name");
		tableFieldConverter.put("introduced", "computer.introduced");
		tableFieldConverter.put("discontinued", "computer.discontinued");
		tableFieldConverter.put("company", "company.name");
		tableOrderConverter.put(true, "ASC");
		tableOrderConverter.put(false, "DESC");
	}
	
	public QueryCreator(PageRequest pageRequest, boolean count) {
		this.pageRequest = pageRequest;
		this.isCount = count;
	}
	
	/**
	 * Create a query.
	 * @return JPQL command as String
	 */
	public String createQuery() {
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT");
		
		if (isCount) {
			sql.append(" COUNT(computer.id)");
		} else {
			sql.append(" NEW com.excilys.core.dto.ComputerDTO(computer.id, computer.name, computer.introduced, computer.discontinued, computer.company) ");
		}
		
		sql.append("FROM Computer computer "
				+ "LEFT OUTER JOIN computer.company company ");
		
		if (pageRequest.getSearchedName() != null || pageRequest.getId() != null) {
			sql.append(" WHERE");
			 
			if (pageRequest.getId() != null) {
				sql.append(" computer.id LIKE " + pageRequest.getId());
			}
			if (pageRequest.getSearchedName() != null) {
				if (pageRequest.getId() != null) {
					sql.append(" AND");
				}
				sql.append(" computer.name LIKE '"  + pageRequest.getSearchedName() + "%'" + " OR company.name LIKE '" + pageRequest.getSearchedName() + "%'");
			}
		}
		if (!isCount) {
			if (pageRequest.getOrderBy() != null) {
				sql.append(" ORDER BY");
				sql.append(" " + tableFieldConverter.get(pageRequest.getOrderBy()));				
				sql.append(" " + tableOrderConverter.get(pageRequest.getOrderAlphaNumerical()));
			}
		}
		return sql.toString();
	}
}
