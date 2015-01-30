package miniblog.dao.implement;

import java.util.List;

import miniblog.dao.interfaces.IArticleDao;
import miniblog.entity.Articles;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("ArticleDaoImpl")
public class ArticleDaoImpl<T extends Articles> extends CommonDaoImpl<Articles> implements IArticleDao<T> {
    // Create a session
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public boolean setStatus(Articles article)
    {
        Session s = sessionFactory.getCurrentSession();
        // begin transaction
        s.beginTransaction();

        String hqlUpdate = "update Articles set status = :newstatus where id = :id";
        s.createQuery(hqlUpdate).setInteger("newstatus", article.getStatus()).setInteger("id", article.getId())
                .executeUpdate();

        s.close();
        return false;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Articles> getArticlesByUser(int userID)
    {
        // create session
        Session s = sessionFactory.getCurrentSession();
        // begin transaction
        s.beginTransaction();
        // set user list when data return
        List<Articles> list = s.createCriteria(Articles.class).add(Restrictions.eq("users_id", userID)).list();
        return list;
    }
}
