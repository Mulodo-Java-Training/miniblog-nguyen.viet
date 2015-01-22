package nguyen.viet.miniblog.serviceImpl;

import java.util.List;




import nguyen.viet.miniblog.DAOInterface.ICommonDao;
import nguyen.viet.miniblog.serviceInterface.ICommonService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public abstract class CommonServiceImpl<T> implements ICommonService<T> {
	@Autowired
	private ICommonDao<T> commonDao;

	private ICommonDao<T> getDao() {
		return commonDao;
	}

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
