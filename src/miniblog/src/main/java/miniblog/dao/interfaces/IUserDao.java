package miniblog.dao.interfaces;

import java.util.List;

import miniblog.entity.Users;

public interface IUserDao<T extends Users> extends ICommonDao<Users>
{
    //get user by username & password
	public Users getUserByIdPassword(String username, String password);
	//get user by lastname and firstname
	public List<Users> getUsersByName(String name);
	//get user by username
	public Users getUsersByUsername(String name);
}