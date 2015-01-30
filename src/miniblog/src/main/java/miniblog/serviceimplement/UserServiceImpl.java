package miniblog.serviceimplement;

import java.util.List;

import miniblog.daointerface.IUserDao;
import miniblog.entity.Users;
import miniblog.serviceinterface.IUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("UserServiceImpl")
public class UserServiceImpl<T extends Users> implements IUserService {

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
    public List<Users> list()
    {
        return this.userDAO.list();
    }

    @Override
    public Users getById(int id)
    {
        return this.userDAO.get(id);
    }

    @Override
    public boolean add(Users user)
    {
        return this.userDAO.save(user);
    }

    @Override
    public boolean delete(int id)
    {
        return this.userDAO.delete(id);
    }

    @Override
    public boolean update(Users user)
    {
        return this.userDAO.update(user);
    }

}
