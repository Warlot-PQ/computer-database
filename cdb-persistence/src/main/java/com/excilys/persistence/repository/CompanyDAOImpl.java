package com.excilys.persistence.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;


import com.excilys.binding.CompanyMapper;
import com.excilys.core.dto.CompanyDTO;
import com.excilys.core.entity.Company;
import com.excilys.core.entity.QCompany;
import com.excilys.persistence.repository.interfaces.CompanyDAO;

import com.mysema.query.SearchResults;
import com.mysema.query.jpa.impl.JPAQuery;

/**
 * DB manipulation on Company table.
 * Singleton class.
 * 
 * @author pqwarlot
 *
 */
@Repository("companyDAO")
@Scope("singleton")
public class CompanyDAOImpl implements CompanyDAO {
	@PersistenceContext
	private EntityManager em;

	public CompanyDAOImpl() {
	}

	@Override
	public List<CompanyDTO> findAll() {
		QCompany qcompany = QCompany.company;
		JPAQuery query = new JPAQuery(em);
		SearchResults<Company> companies = query.from(qcompany).listResults(qcompany);
		List<CompanyDTO> companiesDTO = new ArrayList<>();
		
		for (Company company: companies.getResults()) {
			companiesDTO.add( CompanyMapper.toDTO(company) );
		}
		return companiesDTO;
	}

	@Override
	public void create(Company obj) {
		throw new UnsupportedClassVersionError("Not implemented yet");
	}

	@Override
	public void updateById(Company obj) {
		throw new UnsupportedClassVersionError("Not implemented yet");
	}
	
	@Override
	public CompanyDTO findById(Long id) {
		//TODO for now, QCompany is generated at compile-time and need to be copying manually
		QCompany qcompany = QCompany.company;
		JPAQuery query = new JPAQuery(em);
		Company company = query.from(qcompany).where(qcompany.id.eq(id)).uniqueResult(qcompany);
		
		return (company == null) ? null : CompanyMapper.toDTO(company);
	}	

	@Override
	public void delete(Long id) {
		em.remove(em.find(Company.class, id));
		throw new RuntimeException();
	}

	@Override
	public int getRowNumber() {		
		QCompany qcompany = QCompany.company;
		JPAQuery query = new JPAQuery(em);
		return (int) query.from(qcompany).count();
	}
}
