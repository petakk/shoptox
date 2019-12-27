package com.tox.shoptox;

import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UserLogic {
    private static Logger logger = Logger.getLogger(OrderLogic.class);
    public static User registerNewUser(){
            User user = new User();
            user.setName("user_" + (UserDAO.getUsers().size() + 1));
            user.setPassword("");
            HibernateUtil.getSessionFactory().getCurrentSession().save(user);
            logger.info(String.format("New user#%d registered at %s", user.getId(),new SimpleDateFormat("dd MMM HH:mm", new Locale("eng")).format(new Date())));
            return user;
    }
}
