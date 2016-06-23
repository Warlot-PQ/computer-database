package com.excilys.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.excilys.DTO.ComputerDTO;
import com.excilys.entity.Computer;
import com.excilys.pagination.PageRequest;
import com.excilys.repository.interfaces.ComputerDAO;

/**
 * DB manipulation on Computer table using JPQL
 * Singleton class.
 * 
 * @author pqwarlot
 *
 */
@Repository("computerDAO")
@Scope("singleton")
public class ComputerDAOImpl implements ComputerDAO {
	@PersistenceContext // Inject an EntityManager bean from the EntityManagerFactory
	private EntityManager em;
	
	public ComputerDAOImpl() {
	}

	@Override
	public List<ComputerDTO> findAll(PageRequest pageRequest) {
		final String SQL_QUERYQueryCreator = new QueryCreator(pageRequest, false).createQuery();
		
		Query query = em.createQuery(SQL_QUERYQueryCreator);
		query.setFirstResult(pageRequest.getFirstIndexElt());
		query.setMaxResults(pageRequest.getEltByPage());	
		
		return query.getResultList();
	}
	
	@Override
	public void create(Computer computer) {		
		em.persist(computer);
		em.flush();
	}

	@Override
	public void delete(Long id) {
		em.remove(em.find(Computer.class, id));
		em.flush();
	}

	@Override
	public void updateById(Computer computer) {
		Computer computerToUpdate = em.find(Computer.class, computer.getId());
		
		computerToUpdate.setName(computer.getName());
		computerToUpdate.setIntroduced(computer.getIntroduced());
		computerToUpdate.setDiscontinued(computer.getDiscontinued());
		computerToUpdate.setCompany(computer.getCompany());
		em.flush();
	}

	@Override
	public int count(PageRequest pageRequest) {
		final String JPQL = new QueryCreator(pageRequest, true).createQuery();
		return ((Long) em.createQuery(JPQL).getSingleResult()).intValue();
	}
	
	@Override
	public int deleteByCompany(Long id) {
		List<Computer> computersToDelete = em.createQuery("SELECT c FROM Computer c WHERE c.company = " + id, Computer.class).getResultList();
		
		for (Computer computer: computersToDelete) {
			em.remove(computer);
		}

		return computersToDelete.size();
	}
}
