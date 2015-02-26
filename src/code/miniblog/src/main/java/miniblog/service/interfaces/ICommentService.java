package miniblog.service.interfaces;

import miniblog.entity.Comments;
import miniblog.util.ResultResponse;

public interface ICommentService extends ICommonService<Comments> {
    //get list comments by user
    public ResultResponse listByUser(int user_id);
    //get list comments by article
    public ResultResponse listByPost(int article_id);
}


