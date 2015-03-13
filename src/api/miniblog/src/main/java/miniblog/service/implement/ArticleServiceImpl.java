package miniblog.service.implement;

import java.util.List;

import miniblog.dao.IArticleDao;
import miniblog.dao.ICommonDao;
import miniblog.entity.Articles;
import miniblog.service.IArticleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("ArticleServiceImpl")
public class ArticleServiceImpl<T extends Articles> extends CommonServiceImpl<Articles> implements IArticleService {

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
    public List<Articles> getAllPostByUser(int userID)
    {
        return this.articleDAO.getArticlesByUser(userID);
    }

    @Override
    protected ICommonDao<Articles> getDao()
    {
        return articleDAO;
    }

}
