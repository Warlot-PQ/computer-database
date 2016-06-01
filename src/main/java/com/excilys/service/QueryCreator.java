package com.excilys.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Create a PreparedStatement from a PageRequest and a Connection object.
 * The count option disable LIMIT, OFFSET and ORDER BY options.
 * @author pqwarlot
 *
 */
public class QueryCreator {
	private PageRequest pageRequest;
	private Connection connection;
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
	
	public QueryCreator(PageRequest pageRequest, Connection connection, boolean count) {
		this.pageRequest = pageRequest;
		this.connection = connection;
		this.count = count;
	}
	
	public PreparedStatement createPreparedQuery() throws SQLException {
		StringBuilder sql = new StringBuilder();
		PreparedStatement pstmt = null;
		Map<Integer, Object> mapQuery = new HashMap<>();
		Integer indexQuery = 1;
		
		sql.append("SELECT");
		
		if (count == true) {
			sql.append(" COUNT(computer.id) AS TOTAL,");
		}
		
		sql.append(" computer.id,computer.name,computer.introduced,"
				+ "computer.discontinued,computer.company_id,company.name AS company_name " + "FROM computer "
				+ "LEFT OUTER JOIN company " + "ON computer.company_id=company.id");
		
		if (pageRequest.getComputerName() != null || pageRequest.getComputerId() != null) {
			sql.append(" WHERE");
			 
			if (pageRequest.getComputerId() != null) {
				sql.append(" computer.id=?");
				mapQuery.put(indexQuery, pageRequest.getComputerId());
				indexQuery += 1;
			}
			if (pageRequest.getComputerName() != null) {
				if (pageRequest.getComputerId() != null) {
					sql.append(" AND");
				}
				sql.append(" computer.name=? OR company.name=?");
				mapQuery.put(indexQuery, pageRequest.getComputerName());
				indexQuery += 1;
				mapQuery.put(indexQuery, pageRequest.getComputerName());
				indexQuery += 1;
			}
		}
		if (count == false) {
			if (pageRequest.getOrderBy() != null) {
				sql.append(" ORDER BY");
				sql.append(" " + tableFieldConverter.get(pageRequest.getOrderBy()));				
				sql.append(" " + tableOrderConverter.get(pageRequest.getOrderAlphaNumerical()));
			}
			if (pageRequest.getEltByPage() != null && pageRequest.getPage() != null) {
				sql.append(" LIMIT ?, ?");
				mapQuery.put(indexQuery, getFirstItemNumberWanted(pageRequest.getPage().intValue(), pageRequest.getEltByPage().intValue()));
				indexQuery += 1;
				mapQuery.put(indexQuery, getLastItemNumberWanted(pageRequest.getPage().intValue(), pageRequest.getEltByPage().intValue()));
				indexQuery += 1;
			} else if (pageRequest.getEltByPage() != null) {
				sql.append(" LIMIT ?");
				mapQuery.put(indexQuery, pageRequest.getEltByPage());
				indexQuery += 1;
			}
		}
		
		pstmt = connection.prepareStatement(sql.toString());

		for (Entry<Integer, Object> entry: mapQuery.entrySet()) {
			pstmt.setObject(entry.getKey(), entry.getValue());
		}
		
		return pstmt;
	}

	private int getFirstItemNumberWanted(int currentPage, int limit) {
		return (currentPage - 1) * limit;
	}
	
	private int getLastItemNumberWanted(int currentPage, int limit) {
		return getFirstItemNumberWanted(currentPage, limit) + limit;
	}
}
