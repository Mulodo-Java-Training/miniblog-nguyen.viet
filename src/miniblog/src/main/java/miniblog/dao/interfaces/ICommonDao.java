package miniblog.dao.interfaces;

import java.io.Serializable;
import java.util.List;

public interface ICommonDao<T> {
    //list data
	List<T> list();
	//add a data
	boolean save(T bean);
	//update a data
	boolean update(T bean);
	//delete a data
	boolean delete(Serializable id);
	//get a data by id
	T get(Serializable id);
}