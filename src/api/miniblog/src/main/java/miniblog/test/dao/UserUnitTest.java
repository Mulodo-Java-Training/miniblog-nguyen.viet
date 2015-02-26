package miniblog.test.dao;

import java.util.List;

import miniblog.dao.interfaces.IUserDao;
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
public class UserUnitTest extends XMLConfig{

    // Create userDAO to operation with database
    @Autowired
    @Qualifier("UserDaoImpl")
    private IUserDao<Users> userDAO;
    
    //Create User to test
    private Users users;
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
    }

    @After
    public void tearDown() throws Exception
    {
        //delete user add to test
        userDAO.delete(users.getId());
    }

    //Test Add User
    @Test
    public void testAdd()
    {
        //create user object to add database
        Users userAdd = new Users();
        userAdd.setUsername("test.dao.add");
        userAdd.setPassword("123456");
        userAdd.setFirstname("Test");
        userAdd.setLastname("DAO");
        userAdd.setEmail("testdao.@gmail.com");
        
        //check add to database success 
        Assert.assertTrue(userDAO.save(userAdd));
        //Check add to database faild 
        Assert.assertFalse(userDAO.save(userAdd));
        //delete user just add to test
        userDAO.delete(userAdd.getId());
    }

    //Test Update User
    @Test
    public void testUpdate()
    {
        //edit new firstname update
        this.users.setFirstname("Tran");
        //check update to database success 
        Assert.assertTrue(userDAO.update(users));
        //check update to database faild 
        Assert.assertFalse(userDAO.update(new Users()));
    }
    
    //Test Get By User Id
    @Test
    public void testGetById()
    {
        //Check get User from database success
        Assert.assertNotNull(userDAO.get(this.users.getId()));
    }
    
    //Test Get List User
    @Test
    public void testGetList()
    {
        //Get List  
        List<Users> userData = userDAO.list();
        //Compare user add to database and user get from database
        Assert.assertNotNull(userData);
    }
    
    //Test Get Delete User
    @Test
    public void testDelete()
    {
        //create user object to add database
        Users userAdd = new Users();
        userAdd.setUsername("test.dao.delete");
        userAdd.setPassword("123456");
        userAdd.setFirstname("Test");
        userAdd.setLastname("DAO");
        userAdd.setEmail("testdao.@gmail.com");
        //add user to database
        userDAO.save(userAdd);
        //Check Delete to database success 
        Assert.assertTrue(userDAO.delete(userAdd.getId()));
        //Check Delete to database faild 
        Assert.assertFalse(userDAO.delete(userAdd.getId()));
    }
    
    //Test Get By Username and Password
    @Test
    public void testGetByUsernamePassword()
    {
        //Check get User from database success
        Assert.assertNotNull(userDAO.getUserByIdPassword(this.users.getUsername(), this.users.getPassword()));
    }
    
    //Test Get By Username
    @Test
    public void testGetByUsername()
    {
        //Check get User from database success
        Assert.assertNotNull(userDAO.getUsersByUsername(this.users.getUsername()));
    }
    
    //Test Get By name
    @Test
    public void testGetByName()
    {
        //Check get User from database success
        Assert.assertNotNull(userDAO.getUsersByName(this.users.getLastname()));
    }
}
