package miniblog.serviceimplement;

import miniblog.daointerface.IArticleDao;
import miniblog.entity.Articles;
import miniblog.serviceinterface.IArticleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("iArticleService")
public class ArticleServiceImpl<T extends Articles> extends CommonServiceImpl<Articles> implements IArticleService {

//    Create userDAO to operation with database
//    @Autowired
//    @Qualifier("iArticleDao")
//    private IArticleDao<Articles> articleDAO;
//
//    public void setArticleDao(IArticleDao<Articles> articleDAO)
//    {
//        this.articleDAO = articleDAO;
//    }
//    
//    //Change a post active or deactive
//    @Override
//    public boolean setActive(Articles article)
//    {
//        articleDAO.setStatus(article);
//        return false;
//    }

}
