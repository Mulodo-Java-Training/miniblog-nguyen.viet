package miniblog.service.implement;

import javax.ws.rs.core.MediaType;

import miniblog.api.CommonAPI;
import miniblog.constant.CommonConstant;
import miniblog.entity.Users;
import miniblog.service.interfaces.IUserService;
import miniblog.util.ResultResponse;

import org.jboss.resteasy.client.ClientResponse;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@SuppressWarnings("deprecation")
@Service("UserServiceImpl")
public class UserServiceImpl<T extends Users> implements IUserService {

    // create API Return
    private CommonAPI api;

    public UserServiceImpl() {
        this.api = new CommonAPI();
    }

    // get user by username and password
    @Override
    public ResultResponse getUserByIdPassword(String username, String password)
    {
        String input = "username=" + username + "&password=" + password;
        // operate via API
        ClientResponse<ResultResponse> response = api.processing(CommonConstant.USER_LOGIN_URL, MediaType.APPLICATION_JSON,
                MediaType.APPLICATION_FORM_URLENCODED, input, CommonConstant.METHOD_POST);
        // Create object and get value return
        ResultResponse apiReturn = new ResultResponse();
        apiReturn = api.parseMeta(response);
        return apiReturn;
    }

    // get user by name
    @Override
    public ResultResponse getUsersByName(String name)
    {
        String input = "name=" + name;
        // operate via API
        ClientResponse<ResultResponse> response = api.processing(CommonConstant.USER_SEARCH_URL, MediaType.APPLICATION_JSON,
                MediaType.APPLICATION_FORM_URLENCODED, input, CommonConstant.METHOD_POST);
        // Create object and get value return
        ResultResponse apiReturn = new ResultResponse();
        apiReturn = api.parseMeta(response);
        return apiReturn;
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
        ClientResponse<ResultResponse> response = api.processing(CommonConstant.USER_INFOR_URL+id, MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON,null,
                CommonConstant.METHOD_GET);
        // Create object and get value return
        ResultResponse apiReturn = new ResultResponse();
        apiReturn = api.parseMeta(response);
        return apiReturn;
    }

    @Override
    public ResultResponse add(Users user)
    {
        // create object to parse to json format
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
        // operate via API
        ClientResponse<ResultResponse> response = api.processing(CommonConstant.USER_CREATE_URL,
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, gson.toJson(user), CommonConstant.METHOD_POST);
        // Create object and get value return
        ResultResponse apiReturn = new ResultResponse();
        apiReturn = api.parseMeta(response);
        return apiReturn;
    }

    @Override
    public ResultResponse delete(int id)
    {
        return null;
    }

    @Override
    public ResultResponse update(Users user)
    {   
        // create object to parse to json format
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
        // operate via API
        ClientResponse<ResultResponse> response = api.processing(CommonConstant.USER_UPDATE_URL, MediaType.APPLICATION_JSON,
                MediaType.APPLICATION_JSON, gson.toJson(user), CommonConstant.METHOD_PUT);
        // Create object and get value return
        ResultResponse apiReturn = new ResultResponse();
        apiReturn = api.parseMeta(response);
        return apiReturn;
    }

    @Override
    public ResultResponse changePassword(Users user)
    {
        // create object to parse to json format
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
        // operate via API
        ClientResponse<ResultResponse> response = api.processing(CommonConstant.USER_CHANGEPASS_URL, MediaType.APPLICATION_JSON,
                MediaType.APPLICATION_JSON, gson.toJson(user), CommonConstant.METHOD_PUT);
        // Create object and get value return
        ResultResponse apiReturn = new ResultResponse();
        apiReturn = api.parseMeta(response);
        return apiReturn;
    }

}
