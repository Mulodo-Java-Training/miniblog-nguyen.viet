package miniblog.service.implement;

import javax.ws.rs.core.MediaType;

import miniblog.api.CommonAPI;
import miniblog.constant.CommonConstant;
import miniblog.entity.Comments;
import miniblog.service.interfaces.ICommentService;
import miniblog.util.ResultResponse;

import org.jboss.resteasy.client.ClientResponse;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
@SuppressWarnings("deprecation")
@Service("CommentServiceImpl")
public class CommentServiceImpl<T extends Comments> implements ICommentService {

    
    // create API Return
    private CommonAPI api;

    public CommentServiceImpl() {
        this.api = new CommonAPI();
    }

    @Override
    public ResultResponse list()
    {
        return null;
    }

    @Override
    public ResultResponse getById(int id)
    {
        // operate via API
        ClientResponse<ResultResponse> response = api.processing(CommonConstant.COMMENT_BY_ID_URL + id,
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, null, CommonConstant.METHOD_GET);
        // Create object and get value return
        ResultResponse apiReturn = new ResultResponse();
        apiReturn = api.parseMeta(response);
        return apiReturn;
    }

    @Override
    public ResultResponse add(Comments comment)
    {
        // create object to parse to json format
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
        // operate via API
        ClientResponse<ResultResponse> response = api.processing(CommonConstant.COMMENT_ADD_URL, MediaType.APPLICATION_JSON,
                MediaType.APPLICATION_JSON, gson.toJson(comment), CommonConstant.METHOD_POST);
        // Create object and get value return
        ResultResponse apiReturn = new ResultResponse();
        apiReturn = api.parseMeta(response);
        return apiReturn;
    }

    @Override
    public ResultResponse delete(int id)
    {
        // operate via API
        ClientResponse<ResultResponse> response = api.processing(CommonConstant.COMMENT_DELETE_URL + id,
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, null, CommonConstant.METHOD_DELETE);
        // Create object and get value return
        ResultResponse apiReturn = new ResultResponse();
        apiReturn = api.parseMeta(response);
        return apiReturn;
    }

    @Override
    public ResultResponse update(Comments comment)
    {
        // create object to parse to json format
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
        // operate via API
        ClientResponse<ResultResponse> response = api.processing(CommonConstant.COMMENT_EDIT_URL, MediaType.APPLICATION_JSON,
                MediaType.APPLICATION_JSON, gson.toJson(comment), CommonConstant.METHOD_PUT);
        // Create object and get value return
        ResultResponse apiReturn = new ResultResponse();
        apiReturn = api.parseMeta(response);
        return apiReturn;
    }

    @Override
    public ResultResponse listByUser(int user_id)
    {
        return null;
    }

    @Override
    public ResultResponse listByPost(int article_id)
    {
        // operate via API
        ClientResponse<ResultResponse> response = api.processing(CommonConstant.COMMENT_POST_URL+article_id,
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, null, CommonConstant.METHOD_GET);
        // Create object and get value return
        ResultResponse apiReturn = new ResultResponse();
        apiReturn = api.parseMeta(response);
        return apiReturn;
    }

}
