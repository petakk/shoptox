package com.tox.shoptox;

import java.util.List;

public class UserDAO {

    final static public User createUser(String name, String password) {
        User user = new User();

        user.setName(name);
        user.setPassword(password);

        HibernateUtil.getSessionFactory().getCurrentSession().save(user);

        return user;
    }

    final static public void save(User user) {
        HibernateUtil.getSessionFactory().getCurrentSession().save(user);
    }

    final static public List<Long> getUsers() {
        return HibernateUtil.getSessionFactory().getCurrentSession().createQuery("select id from User").list();
    }

    final static public User get(long id) {
        return (User) HibernateUtil.getSessionFactory().getCurrentSession().createQuery("from User as user where id = :id").setParameter("id", id).uniqueResult();
    }

    final static public User getUser(String name) {
        return (User) HibernateUtil.getSessionFactory().getCurrentSession().createQuery("from User as user where lower(user.name) = :username").setParameter("username", name.toLowerCase()).uniqueResult();
    }

    final static public Long getUsersCount() {
        return (Long) HibernateUtil.getSessionFactory().getCurrentSession().createQuery("select count(*) from User").uniqueResult();
    }

}
