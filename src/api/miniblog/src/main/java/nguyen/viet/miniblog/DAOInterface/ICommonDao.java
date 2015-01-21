package nguyen.viet.miniblog.DAOInterface;

import java.io.Serializable;
import java.util.List;

public interface ICommonDao<T> {
	List<T> list();

	boolean save(T bean);

	boolean update(T bean);

	boolean delete(Serializable id);

	T get(Serializable id);
}