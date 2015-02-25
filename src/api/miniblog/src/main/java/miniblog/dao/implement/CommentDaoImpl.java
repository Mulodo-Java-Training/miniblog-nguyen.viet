package miniblog.dao.implement;

import java.util.List;

import miniblog.dao.interfaces.ICommentDao;
import miniblog.entity.Comments;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("CommentDaoImpl")
public class CommentDaoImpl<T extends Comments> extends CommonDaoImpl<Comments> implements ICommentDao<T> {
    // Create a session
    @Autowired
    private SessionFactory sessionFactory;

    @SuppressWarnings("unchecked")
    @Override
    public List<Comments> listByUser(int id)
    {
        // create session
        Session s = sessionFactory.openSession();
        // set user list when data return
        List<Comments> cmt = s.createCriteria(Comments.class).
                add(Restrictions.eq("users_id", id)).list();
        s.close();
        return cmt;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Comments> listByPost(int articles_id)
    {
        // create session
        Session s = sessionFactory.openSession();
        // set user list when data return
        List<Comments> cmt = s.createCriteria(Comments.class).
                add(Restrictions.eq("articles_id", articles_id)).list();
        s.close();
        return cmt;
    }

}
