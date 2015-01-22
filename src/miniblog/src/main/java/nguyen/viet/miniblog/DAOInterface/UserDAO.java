package nguyen.viet.miniblog.DAOInterface;

import java.util.List;

import nguyen.viet.miniblog.entity.Users;

public interface UserDAO<T extends Users> extends ICommonDao<Users>
{
	public Users getUserByIdPassword(String username, String password);
	public List<Users> getUsersByName();
	public Users getUsersByUsername(String name);
}