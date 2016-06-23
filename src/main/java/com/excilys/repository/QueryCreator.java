package com.excilys.repository;

import java.util.HashMap;
import java.util.Map;

import com.excilys.pagination.PageRequest;

/**
 * Create a JPQL query
 * The count option disable LIMIT, OFFSET and ORDER BY options.
 * @author pqwarlot
 *
 */
public class QueryCreator {
	private PageRequest pageRequest;
	private boolean count;
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
		this.count = count;
	}
	
	/**
	 * Create a SQL query.
	 * @return SQL command
	 */
	public String createQuery() {
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT");
		
		if (count == true) {
			sql.append(" COUNT(computer.id)");
		} else {
			sql.append(" NEW com.excilys.DTO.ComputerDTO(computer.id, computer.name, computer.introduced, computer.discontinued, computer.company) ");
		}
		
		sql.append("FROM Computer computer "
				+ "LEFT OUTER JOIN computer.company company ");
		
		if (pageRequest.getComputerSearchedName() != null || pageRequest.getComputerId() != null) {
			sql.append(" WHERE");
			 
			if (pageRequest.getComputerId() != null) {
				sql.append(" computer.id LIKE " + pageRequest.getComputerId());
			}
			if (pageRequest.getComputerSearchedName() != null) {
				if (pageRequest.getComputerId() != null) {
					sql.append(" AND");
				}
				sql.append(" computer.name LIKE '"  + pageRequest.getComputerSearchedName() + "%'" + " OR company.name LIKE '" + pageRequest.getComputerSearchedName() + "%'");
			}
		}
		if (count == false) {
			if (pageRequest.getOrderBy() != null) {
				sql.append(" ORDER BY");
				sql.append(" " + tableFieldConverter.get(pageRequest.getOrderBy()));				
				sql.append(" " + tableOrderConverter.get(pageRequest.getOrderAlphaNumerical()));
			}
		}
		return sql.toString();
	}
}
