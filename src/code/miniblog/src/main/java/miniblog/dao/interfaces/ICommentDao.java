package miniblog.dao.interfaces;

import java.util.List;

import miniblog.entity.Comments;

public interface ICommentDao<T extends Comments> extends ICommonDao<Comments> {
    //get list comments by user
    public List<Comments> listByUser(int user_id);
    //get list comments by article
    public List<Comments> listByPost(int article_id);
}
