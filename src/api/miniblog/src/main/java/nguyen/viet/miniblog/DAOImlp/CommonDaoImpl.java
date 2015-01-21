package nguyen.viet.miniblog.DAOImlp;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import nguyen.viet.miniblog.DAOInterface.ICommonDao;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

@Repository
public abstract class CommonDaoImpl<T> extends HibernateDaoSupport implements
		ICommonDao<T> {
	private Class<T> entityClass;
	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	public void setSession(SessionFactory sessionFactory) {
		this.setSessionFactory(sessionFactory);
	}

	@SuppressWarnings("unchecked")
	public CommonDaoImpl() {
		entityClass = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	@Override
	public List<T> list() {
		return getHibernateTemplate().loadAll(entityClass);
	}

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

	@Override
	public T get(Serializable id) {
		T o = (T) getHibernateTemplate().get(entityClass, id);
		return o;
	}
}