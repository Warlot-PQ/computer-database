package com.excilys.persistence.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.excilys.binding.UserMapper;
import com.excilys.core.dto.QUser;
import com.excilys.core.dto.UserDTO;
import com.excilys.core.entity.Company;
import com.excilys.core.entity.User;
import com.excilys.persistence.repository.interfaces.UserDAO;
import com.mysema.query.SearchResults;
import com.mysema.query.jpa.impl.JPAQuery;

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
	public List<UserDTO> findAll() {
		QUser quser = QUser.user;
		JPAQuery query = new JPAQuery(em);
		SearchResults<User> users = query.from(quser).listResults(quser);
		List<UserDTO> usersDTO = new ArrayList<>();
		
		for (User user: users.getResults()) {
			usersDTO.add( UserMapper.toDTO(user) );
		}
		return usersDTO;
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
