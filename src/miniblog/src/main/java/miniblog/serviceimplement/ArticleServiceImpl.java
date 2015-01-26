package miniblog.serviceimplement;

import org.springframework.stereotype.Service;

import miniblog.entity.Articles;
import miniblog.serviceinterface.IArticleService;

@Service
public class ArticleServiceImpl<T extends Articles> extends CommonServiceImpl<Articles> implements IArticleService {

}
