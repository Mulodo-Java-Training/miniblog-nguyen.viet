package nguyen.viet.miniblog.serviceInterface;

import nguyen.viet.miniblog.entity.Users;

public interface IUserService  extends ICommonService<Users>{
	public Users getUsersByUsername(String name);
	public Users getUserByIdPassword(String username, String password);
}
