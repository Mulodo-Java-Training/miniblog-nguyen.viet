package nguyen.viet.miniblog.DAOInterface;

import java.util.List;

import nguyen.viet.miniblog.entity.Users;

public interface UserDAO<T extends Users> extends ICommonDao<Users>
{
	public List<Users> getUsersByName();
}