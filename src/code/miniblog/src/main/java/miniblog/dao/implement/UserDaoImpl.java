package miniblog.dao.implement;

import java.util.List;

import miniblog.dao.interfaces.IUserDao;
import miniblog.entity.Users;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
@Repository("UserDaoImpl")
public class UserDaoImpl<T extends Users> extends CommonDaoImpl<Users> implements IUserDao<T> {
    // Create a session
    @Autowired
    private SessionFactory sessionFactory;

    // Search user by lastname or firstname
    @SuppressWarnings("unchecked")
    @Override
    public List<Users> getUsersByName(String name)
    {
        // set name format to find in database
        String nameSearch = "%" + name + "%";
        // create session
        Session s = sessionFactory.getCurrentSession();
        // begin transaction
        s.beginTransaction();
        // set user list when data return
//        List<Users> u = s.createCriteria(Users.class).
//                add(Restrictions.or(
//                        Restrictions.like("firstname", nameSearch)
//                        ,Restrictions.like("lastname", nameSearch)
//                        ,Restrictions.like("username", nameSearch))).list();
        List<Users> u = s.createSQLQuery("SELECT * FROM Users where "
                + " firstname like '" +nameSearch+"' "
                + " or lastname like '" +nameSearch+"' "
                + " or username like '" +nameSearch+"' ")
                .addEntity(Users.class).list();
        s.close();
        return u;
    }

    @Override
    public Users getUsersByUsername(String username)
    {
        // create session
        Session s = sessionFactory.getCurrentSession();
        // begin transaction
        s.beginTransaction();
        // set user object
        Users u = (Users) s.createCriteria(Users.class).add(Restrictions.like("username", username)).uniqueResult();
        return u;
    }

    @Override
    public Users getUserByIdPassword(String username, String password)
    {
        // create session
        Session s = sessionFactory.getCurrentSession();
        // begin transaction
        s.beginTransaction();
        // set user object
        Users u = (Users) s.createCriteria(Users.class).add(Restrictions.like("username", username))
                .add(Restrictions.like("password", password)).uniqueResult();
        return u;
    }
}
