package miniblog.service.implement;

import java.util.List;

import miniblog.dao.interfaces.IArticleDao;
import miniblog.entity.Articles;
import miniblog.service.interfaces.IArticleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("ArticleServiceImpl")
public class ArticleServiceImpl<T extends Articles> implements IArticleService {

    // Create articleDAO to operation with database
    @Autowired
    @Qualifier("ArticleDaoImpl")
    private IArticleDao<Articles> articleDAO;

    public void setArticleDao(IArticleDao<Articles> articleDAO)
    {
        this.articleDAO = articleDAO;
    }

    // Change a post active or deactive
    @Override
    public boolean setActive(Articles article)
    {
        articleDAO.setStatus(article);
        return false;
    }

    @Override
    public List<Articles> list()
    {
        return this.articleDAO.list();
    }

    @Override
    public Articles getById(int id)
    {
        return this.articleDAO.get(id);
    }

    @Override
    public boolean add(Articles obj)
    {
        return this.articleDAO.save(obj);
    }

    @Override
    public boolean delete(int id)
    {
        return this.articleDAO.delete(id);
    }

    @Override
    public boolean update(Articles obj)
    {
        return this.articleDAO.update(obj);
    }

    @Override
    public List<Articles> getAllPostByUser(int userID)
    {
        return this.articleDAO.getArticlesByUser(userID);
    }

}
