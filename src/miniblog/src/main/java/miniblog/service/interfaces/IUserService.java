package miniblog.service.interfaces;

import java.util.List;

import miniblog.entity.Users;

public interface IUserService  extends ICommonService<Users>{
    //get user list by name
    public List<Users> getUsersByName(String name);
    //get user by username
	public Users getUsersByUsername(String username);
	//get user by username and password
	public Users getUserByIdPassword(String username, String password);
}
