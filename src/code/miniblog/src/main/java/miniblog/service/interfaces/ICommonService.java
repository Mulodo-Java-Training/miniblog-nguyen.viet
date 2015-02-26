package miniblog.service.interfaces;

import miniblog.util.ResultResponse;

public interface ICommonService<T> {
    //list object
    ResultResponse list();
    //get object by id
    ResultResponse getById(int id);
    //add a object
    ResultResponse add(T obj);
    //delete object
    ResultResponse delete(int id);
    //update object
    ResultResponse update(T obj);
}
