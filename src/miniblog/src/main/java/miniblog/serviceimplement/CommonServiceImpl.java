package miniblog.serviceimplement;

import java.util.List;

import miniblog.daointerface.ICommonDao;
import miniblog.serviceinterface.ICommonService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public abstract class CommonServiceImpl<T> implements ICommonService<T> {
    // Create commonDAO to operation with database
    @Autowired
    private ICommonDao<T> commonDao;

    private ICommonDao<T> getDao()
    {
        return commonDao;
    }

    // get list object
    @Transactional
    public List<T> list()
    {
        return getDao().list();
    }

    // get object by id
    @Transactional
    public T getById(int id)
    {
        return getDao().get(id);
    }

    // add object
    @Transactional
    public boolean add(T obj)
    {
        return getDao().save(obj);
    }

    // delete object
    @Transactional
    public boolean delete(int id)
    {
        return getDao().delete(id);
    }

    // update object
    @Transactional
    public boolean update(T obj)
    {
        return getDao().update(obj);
    }
}
