package miniblog.daoimplement;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import miniblog.daointerface.IArticleDao;
import miniblog.entity.Articles;
import miniblog.entity.Users;

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
        
        String hqlUpdate = "update Article set status = :newstatus where id = :id";
        s.createQuery(hqlUpdate)
        .setInteger("newstatus", article.getStatus())
        .setInteger("id", article.getId()).executeUpdate();

        s.close();
        return false;
    }

}
