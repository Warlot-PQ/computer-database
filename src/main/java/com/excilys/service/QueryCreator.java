package com.excilys.service;

import java.util.HashMap;
import java.util.Map;

/**
 * Create a PreparedStatement from a PageRequest and a Connection object.
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
			sql.append(" COUNT(computer.id) AS TOTAL,");
		}
		
		sql.append(" computer.id,computer.name,computer.introduced,"
				+ "computer.discontinued,computer.company_id,company.name AS company_name " + "FROM computer "
				+ "LEFT OUTER JOIN company " + "ON computer.company_id=company.id");
		
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
			if (pageRequest.getEltByPage() != null && pageRequest.getPage() != null) {
				sql.append(" LIMIT " + getFirstItemNumberWanted(pageRequest.getPage().intValue(), pageRequest.getEltByPage().intValue()) + ", " + getLastItemNumberWanted(pageRequest.getPage().intValue(), pageRequest.getEltByPage().intValue()));
			} else if (pageRequest.getEltByPage() != null) {
				sql.append(" LIMIT " + pageRequest.getEltByPage());
			}
		}
		return sql.toString();
	}

	/**
	 * Calculate the first item index of a specific page.
	 * @param currentPage page wanted
	 * @param limit number of item in the page
	 * @return f item index of the page
	 */
	private int getFirstItemNumberWanted(int currentPage, int limit) {
		return (currentPage - 1) * limit;
	}
	
	/**
	 * Calculate the last item index of a specific page.
	 * @param currentPage page wanted
	 * @param limit number of item in the page
	 * @return last item index of the page
	 */
	private int getLastItemNumberWanted(int currentPage, int limit) {
		return getFirstItemNumberWanted(currentPage, limit) + limit;
	}
}
