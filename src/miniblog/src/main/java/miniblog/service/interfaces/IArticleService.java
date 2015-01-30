package miniblog.service.interfaces;

import java.util.List;

import miniblog.entity.Articles;

public interface IArticleService extends ICommonService<Articles>{
   public boolean setActive(Articles article);
   public List<Articles> getAllPostByUser(int userID);
}
