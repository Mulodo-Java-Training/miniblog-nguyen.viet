package nguyen.viet.miniblog.serviceInterface;

import java.util.List;

public interface ICommonService<T> {
	List<T> list();

	T getById(int id);

	boolean add(T obj);

	boolean delete(int id);

	boolean update(T obj);
}
