package com.excilys.persistence.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.excilys.core.entity.Company;
import com.excilys.core.entity.QUser;
import com.excilys.core.entity.User;
import com.excilys.persistence.repository.interfaces.UserDAO;
import com.mysema.query.jpa.impl.JPAQuery;

/**
 * DB manipulation on User and UserRole entity using JPA and HQL.
 * Singleton class.
 * 
 * @author pqwarlot
 *
 */
@Repository("userDAO")
@Scope("singleton")
public class UserDAOImpl implements UserDAO {
	@PersistenceContext
	private EntityManager em;

	public UserDAOImpl() {
	}
	
	/* (non-Javadoc)
	 * @see com.excilys.persistence.repository.UserDAO#findAll()
	 */
	@Override
	public List<User> findAll() {
		QUser quser = QUser.user;
		JPAQuery query = new JPAQuery(em);
		List<User> users = query.from(quser).listResults(quser).getResults();
		
		return users;
	}
	
	/* (non-Javadoc)
	 * @see com.excilys.persistence.repository.UserDAO#create(com.excilys.core.entity.User)
	 */
	@Override
	public void create(User obj) {
		em.persist(obj);
	}
	
	/* (non-Javadoc)
	 * @see com.excilys.persistence.repository.UserDAO#updateByName(com.excilys.core.entity.User)
	 */
	@Override
	public void updateByName(User obj) {
		User userToUpdate = em.find(User.class, obj.getUsername());
		
		userToUpdate.setPassword(obj.getPassword());
		userToUpdate.setEnabled(obj.isEnabled());
	}
	
	/* (non-Javadoc)
	 * @see com.excilys.persistence.repository.UserDAO#findByUserName(java.lang.String)
	 */
	@Override
	public User findByUserName(String username) {
		QUser quser= QUser.user;
		JPAQuery query = new JPAQuery(em);
		User user = query.from(quser).where(quser.username.eq(username)).uniqueResult(quser);
		
		return user;
	}
	
	/* (non-Javadoc)
	 * @see com.excilys.persistence.repository.UserDAO#delete(java.lang.String)
	 */
	@Override
	public void delete(String username) {
		em.remove(em.find(Company.class, username));
	}
}
