package nguyen.viet.miniblog.serviceImpl;

import nguyen.viet.miniblog.DAOInterface.UserDAO;
import nguyen.viet.miniblog.entity.Users;
import nguyen.viet.miniblog.serviceInterface.IUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl<T extends Users> extends CommonServiceImpl<Users> implements IUserService {
	
	@Autowired
    private UserDAO<Users> userDAO;
	public void setUserDAO(UserDAO<Users> employeeDAO) {
		this.userDAO = employeeDAO;
	}
}
