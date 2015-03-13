package miniblog.service.implement;

import java.util.List;

import miniblog.dao.ICommonDao;
import miniblog.service.ICommonService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public abstract class CommonServiceImpl<T> implements ICommonService<T> {
    
    @SuppressWarnings("unused")
    private Class< T > clazz;

    @Autowired 
    protected ApplicationEventPublisher eventPublisher;

    public CommonServiceImpl() {}
    
    public CommonServiceImpl( final Class< T > clazzToSet ) {
        super();

        clazz = clazzToSet;
    }
    
	//private ICommonDao<T> commonDao;
	protected abstract ICommonDao<T> getDao();

	@Transactional
	public List<T> list() {
		return getDao().list();
	}

	@Transactional
	public T getById(int id) {
		return getDao().get(id);
	}

	@Transactional
	public boolean add(T obj) {
		return getDao().save(obj);
	}

	@Transactional
	public boolean delete(int id) {
		return getDao().delete(id);
	}

	@Transactional
	public boolean update(T obj) {
		return getDao().update(obj);
	}
}
