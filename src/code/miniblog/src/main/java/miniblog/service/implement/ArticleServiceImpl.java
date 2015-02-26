package miniblog.service.implement;

import javax.ws.rs.core.MediaType;

import miniblog.api.CommonAPI;
import miniblog.constant.CommonConstant;
import miniblog.entity.Articles;
import miniblog.service.interfaces.IArticleService;
import miniblog.util.ResultResponse;

import org.jboss.resteasy.client.ClientResponse;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@SuppressWarnings("deprecation")
@Service("ArticleServiceImpl")
public class ArticleServiceImpl<T extends Articles> implements IArticleService {

    // create API Return
    private CommonAPI api;

    public ArticleServiceImpl() {
        this.api = new CommonAPI();
    }

    // Change a post active or deactive
    @Override
    public boolean setActive(Articles article)
    {
        // articleDAO.setStatus(article);
        return false;
    }

    @Override
    public ResultResponse list()
    {
        // operate via API
        ClientResponse<ResultResponse> response = api.processing(CommonConstant.POST_LIST_URL,
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, null, CommonConstant.METHOD_GET);
        // Create object and get value return
        ResultResponse apiReturn = new ResultResponse();
        apiReturn = api.parseMeta(response);
        return apiReturn;
    }

    @Override
    public ResultResponse getById(int id)
    {
        // operate via API
        ClientResponse<ResultResponse> response = api.processing(CommonConstant.POST_BY_ID_URL + id,
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, null, CommonConstant.METHOD_GET);
        // Create object and get value return
        ResultResponse apiReturn = new ResultResponse();
        apiReturn = api.parseMeta(response);
        return apiReturn;
    }

    @Override
    public ResultResponse add(Articles article)
    {
        // create object to parse to json format
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
        // operate via API
        ClientResponse<ResultResponse> response = api.processing(CommonConstant.POST_ADD_URL,
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, gson.toJson(article),
                CommonConstant.METHOD_POST);
        // Create object and get value return
        ResultResponse apiReturn = new ResultResponse();
        apiReturn = api.parseMeta(response);
        return apiReturn;
    }

    @Override
    public ResultResponse delete(int id)
    {
        // operate via API
        ClientResponse<ResultResponse> response = api.processing(CommonConstant.POST_DELETE_URL + id,
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, null, CommonConstant.METHOD_DELETE);
        // Create object and get value return
        ResultResponse apiReturn = new ResultResponse();
        apiReturn = api.parseMeta(response);
        return apiReturn;
    }

    @Override
    public ResultResponse update(Articles article)
    {
        // create object to parse to json format
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
        // operate via API
        ClientResponse<ResultResponse> response = api.processing(CommonConstant.POST_EDIT_URL,
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, gson.toJson(article),
                CommonConstant.METHOD_PUT);
        // Create object and get value return
        ResultResponse apiReturn = new ResultResponse();
        apiReturn = api.parseMeta(response);
        return apiReturn;
    }

    @Override
    public ResultResponse getAllPostByUser(int userID)
    {
        // operate via API
        ClientResponse<ResultResponse> response = api.processing(CommonConstant.POST_USER_URL + userID,
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, null, CommonConstant.METHOD_GET);
        // Create object and get value return
        ResultResponse apiReturn = new ResultResponse();
        apiReturn = api.parseMeta(response);
        return apiReturn;
    }

}
