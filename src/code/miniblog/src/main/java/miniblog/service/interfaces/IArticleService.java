package miniblog.service.interfaces;

import miniblog.entity.Articles;
import miniblog.util.ResultResponse;

public interface IArticleService extends ICommonService<Articles> {
    // change active/deactive a post
    public boolean setActive(Articles article);

    // get list post by user
    public ResultResponse getAllPostByUser(int userID);
}
