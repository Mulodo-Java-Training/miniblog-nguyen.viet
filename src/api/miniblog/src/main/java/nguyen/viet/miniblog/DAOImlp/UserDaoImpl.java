package nguyen.viet.miniblog.DAOImlp;

import java.util.List;

import nguyen.viet.miniblog.DAOInterface.UserDAO;
import nguyen.viet.miniblog.entity.Users;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl<T extends Users> extends CommonDaoImpl<Users> implements UserDAO<T>  {
	@Autowired
    private SessionFactory sessionFactory;

	@Override
	public List<Users> getUsersByName() {
		// TODO Auto-generated method stub
		return null;
	}
}
