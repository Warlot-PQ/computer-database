package com.excilys.persistence.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.excilys.core.dto.ComputerDTO;
import com.excilys.core.entity.Computer;
import com.excilys.persistence.pagination.PageRequest;
import com.excilys.persistence.repository.ComputerDAO;

/**
 * DB manipulation on Computer entity using JPQL.
 * 
 * @author pqwarlot
 *
 */
@Repository("computerDAO")
@Scope("singleton")
public class ComputerDAOImpl implements ComputerDAO {
	@PersistenceContext // Inject an EntityManager bean from the EntityManagerFactory
	private EntityManager em;
	private Logger logger = LoggerFactory.getLogger(ComputerDAOImpl.class);
	
	public ComputerDAOImpl() {
	}

	@Override
	public List<ComputerDTO> findAll(PageRequest pageRequest) {
		final String SQL_QUERYQueryCreator = new QueryCreator(pageRequest, false).createQuery();
		logger.info("JPQL find all: " + SQL_QUERYQueryCreator);
		
		Query query = em.createQuery(SQL_QUERYQueryCreator);
		query.setFirstResult(pageRequest.getFirstIndexElt());
		query.setMaxResults(pageRequest.getEltByPage());	
		
		return query.getResultList();
	}
	
	@Override
	public void create(Computer computer) {
		em.persist(computer);
	}

	@Override
	public void delete(Long id) {
		em.remove(em.find(Computer.class, id));
	}

	@Override
	public void updateById(Computer computer) {
		Computer computerToUpdate = em.find(Computer.class, computer.getId());
		
		computerToUpdate.setName(computer.getName());
		computerToUpdate.setIntroduced(computer.getIntroduced());
		computerToUpdate.setDiscontinued(computer.getDiscontinued());
		computerToUpdate.setCompany(computer.getCompany());
	}

	@Override
	public int count(PageRequest pageRequest) {
		final String JPQL = new QueryCreator(pageRequest, true).createQuery();
		logger.info("JPQL count: " + JPQL);
		return ((Long) em.createQuery(JPQL).getSingleResult()).intValue();
	}
	
	@Override
	public int deleteByCompany(Long id) {
		final String SQL_DELETE = "SELECT c FROM Computer c WHERE c.company = " + id;
		logger.info("JPQL delete: " + SQL_DELETE);
		List<Computer> computersToDelete = em.createQuery(SQL_DELETE, Computer.class).getResultList();
		
		for (Computer computer: computersToDelete) {
			em.remove(computer);
		}

		return computersToDelete.size();
	}
}
