package miniblog.service.interfaces;

import miniblog.entity.Users;
import miniblog.util.ResultResponse;

public interface IUserService  extends ICommonService<Users>{
    //get user list by name
    public ResultResponse getUsersByName(String name);
	//get user by username and password
	public ResultResponse getUserByIdPassword(String username, String password);
	//change password 
    public ResultResponse changePassword(Users user);
	
}
