package miniblog.service.interfaces;

import java.util.List;

public interface ICommonService<T> {
    //list object
	List<T> list();
	//get object by id
	T getById(int id);
	//add a object
	boolean add(T obj);
	//delete object
	boolean delete(int id);
	//update object
	boolean update(T obj);
}
