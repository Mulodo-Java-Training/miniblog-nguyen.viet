package miniblog.service.interfaces;

import java.util.List;

import miniblog.entity.Articles;

public interface IArticleService extends ICommonService<Articles> {
    // change active/deactive a post
    public boolean setActive(Articles article);

    // get list post by user
    public List<Articles> getAllPostByUser(int userID);
    
}
