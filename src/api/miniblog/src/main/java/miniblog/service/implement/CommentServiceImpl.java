package miniblog.service.implement;

import java.util.List;

import miniblog.dao.ICommentDao;
import miniblog.dao.ICommonDao;
import miniblog.entity.Comments;
import miniblog.service.ICommentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("CommentServiceImpl")
public class CommentServiceImpl<T extends Comments> extends CommonServiceImpl<Comments> implements ICommentService {

    // Create commentDAO to operation with database
    @Autowired
    @Qualifier("CommentDaoImpl")
    private ICommentDao<Comments> commentDAO;
    

    public void setCommentDao(ICommentDao<Comments> commentDAO)
    {
        this.commentDAO = commentDAO;
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

    @Override
    protected ICommonDao<Comments> getDao()
    {
        return commentDAO;
    }

}
