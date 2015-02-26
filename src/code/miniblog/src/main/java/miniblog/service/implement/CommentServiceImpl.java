package miniblog.service.implement;

import java.util.List;

import miniblog.dao.interfaces.ICommentDao;
import miniblog.entity.Comments;
import miniblog.service.interfaces.ICommentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("CommentServiceImpl")
public class CommentServiceImpl<T extends Comments> implements ICommentService {

    // Create commentDAO to operation with database
    @Autowired
    @Qualifier("CommentDaoImpl")
    private ICommentDao<Comments> commentDAO;

    public void setCommentDao(ICommentDao<Comments> commentDAO)
    {
        this.commentDAO = commentDAO;
    }

    @Override
    public List<Comments> list()
    {
        return this.commentDAO.list();
    }

    @Override
    public Comments getById(int id)
    {
        return this.commentDAO.get(id);
    }

    @Override
    public boolean add(Comments obj)
    {
        return this.commentDAO.save(obj);
    }

    @Override
    public boolean delete(int id)
    {
        return this.commentDAO.delete(id);
    }

    @Override
    public boolean update(Comments obj)
    {
        return this.commentDAO.update(obj);
    }

    @Override
    public List<Comments> listByUser(int user_id)
    {
        return this.commentDAO.listByUser(user_id);
    }

    @Override
    public List<Comments> listByPost(int article_id)
    {
        return this.commentDAO.listByPost(article_id);
    }

}
