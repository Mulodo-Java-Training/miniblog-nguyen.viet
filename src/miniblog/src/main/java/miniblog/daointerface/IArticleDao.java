package miniblog.daointerface;

import miniblog.entity.Articles;

public interface IArticleDao <T extends Articles> extends ICommonDao<Articles>{
    // upadte status of a article
    public boolean setStatus(Articles article);
}
