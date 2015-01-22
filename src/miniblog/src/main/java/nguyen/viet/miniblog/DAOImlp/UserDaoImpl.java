package nguyen.viet.miniblog.DAOImlp;

import java.util.List;

import nguyen.viet.miniblog.DAOInterface.UserDAO;
import nguyen.viet.miniblog.entity.Users;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl<T extends Users> extends CommonDaoImpl<Users>
		implements UserDAO<T> {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<Users> getUsersByName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Users getUsersByUsername(String name) {
		Session s = sessionFactory.getCurrentSession();
		s.beginTransaction();
		Users u = (Users) s.createCriteria(Users.class)
				.add(Restrictions.like("username", name)).uniqueResult();
		return u;
	}

	@Override
	public Users getUserByIdPassword(String username, String password) {
		Session s = sessionFactory.getCurrentSession();
		s.beginTransaction();
		Users u = (Users) s.createCriteria(Users.class)
				.add(Restrictions.like("username", username))
				.add(Restrictions.like("password", password)).uniqueResult();
		return u;
	}
}
