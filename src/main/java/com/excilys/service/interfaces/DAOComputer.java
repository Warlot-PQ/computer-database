package com.excilys.service.interfaces;

import java.sql.Connection;
import java.util.List;

import com.excilys.bean.Computer;
import com.excilys.bean.ComputerDTO;
import com.excilys.service.PageRequest;

public interface DAOComputer {
	public List<ComputerDTO> findAll(PageRequest pageRequest, Connection connection);

//	public List<ComputerDTO> findFromTo(int offset, int limit, Connection con);

	public void create(Computer obj);

	public void updateById(Computer obj);

	public void delete(Long id);

	public int getRowNumber(PageRequest pageRequest, Connection con);
	
	public void deleteByCompany(Long id, Connection con);
}
