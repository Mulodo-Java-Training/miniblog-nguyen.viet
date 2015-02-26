package miniblog.constant;

public class CommonConstant {
    //Type result return page
    public static final int DIALOG_SUCCESS = 1;
    public static final int MESS_FAILD = 2;
    public static final int MESS_SUCCESS = 3;
    
    //Method type
    public static final int METHOD_POST = 1;
    public static final int METHOD_GET = 2;
    public static final int METHOD_PUT = 3;
    public static final int METHOD_DELETE = 4;
    
    //Object type
    public static final int TYPE_USER = 1;
    public static final int TYPE_USER_LIST = 2;
    public static final int TYPE_POST = 3;
    public static final int TYPE_POST_LIST = 4;
    public static final int TYPE_COMMENT = 5;
    public static final int TYPE_COMMENT_LIST = 6;
    
    public static final String URL_API = "http://localhost:8080/miniblog/";
    //User URL
    public static final String USER_CREATE_URL = URL_API +"users/add";
    public static final String USER_DELETE_URL = URL_API +"users/delete/";
    public static final String USER_LOGIN_URL = URL_API +"users/login";
    public static final String USER_INFOR_URL = URL_API +"users/infor/";
    public static final String USER_UPDATE_URL = URL_API +"users/update";
    public static final String USER_CHANGEPASS_URL = URL_API +"users/changePass";
    public static final String USER_SEARCH_URL = URL_API +"users/search";
    
    //POST URL
    public static final String POST_ADD_URL = URL_API +"posts/add";
    public static final String POST_ACTIVE_URL = URL_API +"posts/status";
    public static final String POST_EDIT_URL = URL_API +"posts/edit";
    public static final String POST_DELETE_URL = URL_API +"posts/delete/";
    public static final String POST_LIST_URL = URL_API +"posts/list";
    public static final String POST_USER_URL = URL_API +"posts/listby/";
    public static final String POST_BY_ID_URL = URL_API +"posts/";
    
    //COMMENT URL
    public static final String COMMENT_ADD_URL = URL_API +"comments/add";
    public static final String COMMENT_DELETE_URL = URL_API +"comments/delete/";
    public static final String COMMENT_EDIT_URL = URL_API +"comments/edit";
    public static final String COMMENT_USER_URL = URL_API +"comments/user/";
    public static final String COMMENT_POST_URL = URL_API +"comments/post/";
    public static final String COMMENT_BY_ID_URL = URL_API +"comments/";
}
