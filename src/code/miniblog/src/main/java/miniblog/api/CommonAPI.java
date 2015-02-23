package miniblog.api;

import java.io.IOException;
import java.util.List;

import miniblog.constant.CommonConstant;
import miniblog.entity.Articles;
import miniblog.entity.Comments;
import miniblog.entity.Users;
import miniblog.util.ResultResponse;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@SuppressWarnings("deprecation")
public class CommonAPI {

    public ClientResponse<ResultResponse> processing(String url, String inputFormat, String ouputFormat, String data,
            int method)
    {
        ClientRequest request = new ClientRequest(url);
        if (data != null)
        {
            // accept with input type
            request.accept(inputFormat);
            // get data
            request.body(ouputFormat, data);
        }
        // run operation and get respond
        ClientResponse<ResultResponse> response;
        try
        {
            if (method == CommonConstant.METHOD_POST)
                response = request.post(ResultResponse.class);
            else if (method == CommonConstant.METHOD_GET)
                response = request.get(ResultResponse.class);
            else if (method == CommonConstant.METHOD_PUT)
                response = request.put(ResultResponse.class);
            else
                response = request.delete(ResultResponse.class);
        } catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        return response;
    }

    @SuppressWarnings("unused")
    public ResultResponse parseMeta(ClientResponse<ResultResponse> response)
    {
        // Create json object
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
        // Parse result return to ResultResponse object
        ResultResponse result = gson.fromJson(response.getEntity(String.class), ResultResponse.class);
        // Parse Data object(json format) of ResultResponse to Users
        ResultResponse statusReturn = null;
        try
        {
            return statusReturn = new ObjectMapper().readValue(gson.toJson(result), ResultResponse.class);
        } catch (JsonParseException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonMappingException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // close environment
        response.close();
        return null;
    }

    public Object parseData(ResultResponse data, int type)
    {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
        Users user = new Users();
        Articles article = new Articles();
        Comments comment = new Comments();
        try
        {
            if (type == CommonConstant.TYPE_USER)
            {
                user = new ObjectMapper().readValue(gson.toJson(data.getData()), Users.class);
                return user;
            }
            if (type == CommonConstant.TYPE_USER_LIST)
            {
                List<Users> list = new ObjectMapper().readValue(gson.toJson(data.getData()),
                        new TypeReference<List<Users>>() {
                        });
                return list;
            }
            if (type == CommonConstant.TYPE_POST)
            {
                article = new ObjectMapper().readValue(gson.toJson(data.getData()), Articles.class);
                return article;
            }
            if (type == CommonConstant.TYPE_POST_LIST)
            {
                List<Articles> list = new ObjectMapper().readValue(gson.toJson(data.getData()),
                        new TypeReference<List<Articles>>() {
                        });
                return list;
            }
            if (type == CommonConstant.TYPE_COMMENT)
            {
                comment = new ObjectMapper().readValue(gson.toJson(data.getData()), Comments.class);
                return comment;
            }
            if (type == CommonConstant.TYPE_COMMENT_LIST)
            {
                List<Comments> list = new ObjectMapper().readValue(gson.toJson(data.getData()),
                        new TypeReference<List<Comments>>() {
                        });
                return list;
            }

        } catch (JsonParseException e)
        {
            e.printStackTrace();
        } catch (JsonMappingException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
