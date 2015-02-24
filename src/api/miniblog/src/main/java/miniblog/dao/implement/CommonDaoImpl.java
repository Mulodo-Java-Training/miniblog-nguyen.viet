package miniblog.dao.implement;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import miniblog.dao.interfaces.ICommonDao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

@Repository
public abstract class CommonDaoImpl<T> extends HibernateDaoSupport implements
		ICommonDao<T> {
    //create entity class
	private Class<T> entityClass;
	//create session 
	@Autowired
	private SessionFactory sessionFactory;

	//set session
	@Autowired
	public void setSession(SessionFactory sessionFactory) {
		this.setSessionFactory(sessionFactory);
	}

	//create contructor and set value for entity
	@SuppressWarnings("unchecked")
	public CommonDaoImpl() {
		entityClass = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	// Use HibernateDaoSupport to get list data
	@Override
	public List<T> list() {
		return getHibernateTemplate().loadAll(entityClass);
	}

	// Use HibernateDaoSupport to add a date
	@Override
	public boolean save(T bean) {
		Boolean flag = false;
		try {
			getHibernateTemplate().save(bean);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}
	// Use HibernateDaoSupport to update a data
	@Override
	public boolean update(T bean) {
		Boolean flag = false;
		try {
			getHibernateTemplate().update(bean);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	// Use HibernateDaoSupport to delete a data
	@Override
	public boolean delete(Serializable id) {
		Boolean flag = false;
		try {
			T t = get(id);
			getHibernateTemplate().delete(t);
			flag = true;
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	// Use HibernateDaoSupport to get a data by id
	@Override
	public T get(Serializable id) {
		T o = (T) getHibernateTemplate().get(entityClass, id);
		return o;
	}
}