package miniblog.test.dao;

import miniblog.dao.interfaces.IArticleDao;
import miniblog.dao.interfaces.ICommentDao;
import miniblog.dao.interfaces.IUserDao;
import miniblog.entity.Articles;
import miniblog.entity.Comments;
import miniblog.entity.Users;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class CommentUnitTest extends XMLConfig{

    // Create userDAO to operation with database
    @Autowired
    @Qualifier("UserDaoImpl")
    private IUserDao<Users> userDAO;
    
    // Create articleDAO to operation with database
    @Autowired
    @Qualifier("ArticleDaoImpl")
    private IArticleDao<Articles> articleDAO;
    
    // Create commentDAO to operation with database
    @Autowired
    @Qualifier("CommentDaoImpl")
    private ICommentDao<Comments> commentDAO;
    
    //Create User to test
    private Users users;
    
    //Create Post to test
    private Articles posts;
    
    //Create Comment to test;
    private Comments comments;

    @Before
    public void setUp() throws Exception
    {
        //Create user object to test
        Users user = new Users();
        user.setUsername("test.dao");
        user.setPassword("123456");
        user.setFirstname("Test");
        user.setLastname("DAO");
        user.setEmail("testdao.@gmail.com");
        //Store user
        this.users = user;
        //Add user to database
        userDAO.save(this.users);
        
        //Create a post to test
        Articles article = new Articles();
        article.setTitle("test dao post");
        article.setUsers_id(this.users.getId());
        article.setDescription("Test post");
        //Store post
        this.posts = article;
        //Add post to database
        articleDAO.save(this.posts);
        
        //Create a comemnt to test
        Comments cmt = new Comments();
        cmt.setArticles_id(this.posts.getId());
        cmt.setUsers_id(this.users.getId());
        cmt.setDescription("Test Comment");
        //Store comment
        this.comments = cmt;
        //Add comment to database
        commentDAO.save(comments);
        
    }

    @After
    public void tearDown() throws Exception
    {
        //Delete User added to test
        userDAO.delete(this.users.getId());
        //Delete Post added to test
        articleDAO.delete(this.posts.getId());
        //Delete Comment added to test
        commentDAO.delete(this.comments.getId());
    }

    //Test get list of post by user id
    @Test
    public void testListByUser()
    {
        //Check list of post
        Assert.assertNotNull(commentDAO.listByUser(this.users.getId()));
    }

    //Test get list of post by post id
    @Test
    public void testListByPost()
    {
        //Check list of post
        Assert.assertNotNull(commentDAO.listByPost(this.posts.getId()));
    }

    //Test get list of post
    @Test
    public void testList()
    {
        //Check list of post
        Assert.assertNotNull(commentDAO.list());
    }

    //Test add new comment
    @Test
    public void testSave()
    {
        //Create a comemnt to test
        Comments cmt = new Comments();
        cmt.setArticles_id(this.posts.getId());
        cmt.setUsers_id(this.users.getId());
        cmt.setDescription("Test Add Comment");
        //Check add comment to database success
        Assert.assertTrue(commentDAO.save(cmt));
        //Set comment faild
        cmt.setDescription(null);
        //Check add comment to database faild
        Assert.assertFalse(commentDAO.save(cmt));
        //Delete comment just add
        commentDAO.delete(cmt.getId());
    }

    //Test update comment
    @Test
    public void testUpdate()
    {
        //change comment description
        this.comments.setDescription("update comemnt");
        //check update comemnt success
        Assert.assertTrue(commentDAO.update(this.comments));
        //change comment description faild
        this.comments.setDescription(null);
        //check update comemnt success
        Assert.assertFalse(commentDAO.update(this.comments));
    }

    //Test delete comment
    @Test
    public void testDelete()
    {
        //Create a comemnt to test
        Comments cmt = new Comments();
        cmt.setArticles_id(this.posts.getId());
        cmt.setUsers_id(this.users.getId());
        cmt.setDescription("Test Delete Comment");
        //add comment to database
        commentDAO.save(cmt);
        //check delete comment success
        Assert.assertTrue(commentDAO.delete(cmt.getId()));
        //check delete comment faild
        Assert.assertFalse(commentDAO.delete(cmt.getId()));
    }

    //Test get comment by id
    @Test
    public void testGet()
    {
        //Check get comment
        Assert.assertNotNull(commentDAO.get(this.comments.getId()));
    }

}
