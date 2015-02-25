package miniblog.test.dao;

import miniblog.dao.interfaces.IArticleDao;
import miniblog.dao.interfaces.IUserDao;
import miniblog.entity.Articles;
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
public class PostUnitTest extends XMLConfig{
    
    // Create userDAO to operation with database
    @Autowired
    @Qualifier("UserDaoImpl")
    private IUserDao<Users> userDAO;
    
    // Create articleDAO to operation with database
    @Autowired
    @Qualifier("ArticleDaoImpl")
    private IArticleDao<Articles> articleDAO;
    
    //Create User to test
    private Users users;
    
    //Create Post to test
    private Articles posts;

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
    }

    @After
    public void tearDown() throws Exception
    {
        //Delete User added to test
        userDAO.delete(this.users.getId());
        //Delete Post added to test
        articleDAO.delete(this.posts.getId());
    }

    //Test change Status of Post
    @Test
    public void testSetStatus()
    {
        //set status
        Articles article = articleDAO.get(this.posts.getId());
        //Check status default before change
        Assert.assertEquals(article.getStatus(), 0);
        //Change status
        article.setStatus(1);
        //Check and update in database success
        Assert.assertTrue(articleDAO.setStatus(article));
    }

    //Test get all post of user by user id
    @Test
    public void testGetArticlesByUser()
    {
        //Check get Post by User Id
        Assert.assertNotNull(articleDAO.getArticlesByUser(this.users.getId()));
    }

    //Test get all posts
    @Test
    public void testList()
    {
        //Check get all Posts
        Assert.assertNotNull(articleDAO.list());
    }
    
    //Test add new post
    @Test
    public void testSave()
    {
        //Create a post to test
        Articles article = new Articles();
        article.setTitle("test dao add new post");
        article.setUsers_id(this.users.getId());
        article.setDescription("Test post");
        //Check add success
        Assert.assertTrue(articleDAO.save(article));
        //Check add faild
        article.setTitle(null);;
        Assert.assertFalse(articleDAO.save(article));
        //Delete a post just add
        articleDAO.delete(article.getId());
    }

    //Test update a post
    @Test
    public void testUpdate()
    {
        //Change description of post
        this.posts.setDescription("update");
        //Check update success
        Assert.assertTrue(articleDAO.update(this.posts));
        //Check update faild
        this.posts.setTitle(null);;
        Assert.assertFalse(articleDAO.update(this.posts));
    }

    //Test Delete a post
    @Test
    public void testDelete()
    {
        //Create a post to test delete
        Articles article = new Articles();
        article.setTitle("test dao delete post");
        article.setUsers_id(this.users.getId());
        article.setDescription("Test post");
        //Check add success
        Assert.assertTrue(articleDAO.save(article));
        //Check delete a post just add success
        Assert.assertTrue(articleDAO.delete(article.getId()));
        //Check delete a post just add faild
        Assert.assertFalse(articleDAO.delete(article.getId()));
    }

    //Test get a post by id
    @Test
    public void testGet()
    {
       //Check get a post
       Assert.assertNotNull(articleDAO.get(this.posts.getId()));
    }

}
