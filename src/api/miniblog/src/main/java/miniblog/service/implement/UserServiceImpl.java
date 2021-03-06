package miniblog.service.implement;

import java.util.List;

import miniblog.dao.ICommonDao;
import miniblog.dao.IUserDao;
import miniblog.entity.Users;
import miniblog.service.IUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("UserServiceImpl")
public class UserServiceImpl<T extends Users> extends CommonServiceImpl<Users> implements IUserService  {

    // Create userDAO to operation with database
    @Autowired
    @Qualifier("UserDaoImpl")
    private IUserDao<Users> userDAO;
    
    public void setUserDAO(IUserDao<Users> user)
    {
        this.userDAO = user;
    }

    // get user by username
    @Override
    public Users getUsersByUsername(String username)
    {
        return this.userDAO.getUsersByUsername(username);
    }

    // get user by username and password
    @Override
    public Users getUserByIdPassword(String username, String password)
    {
        return this.userDAO.getUserByIdPassword(username, password);
    }

    // get user by name
    @Override
    public List<Users> getUsersByName(String name)
    {
        return this.userDAO.getUsersByName(name);
    }

    @Override
    protected ICommonDao<Users> getDao()
    {
        // TODO Auto-generated method stub
        return userDAO;
    }


}
