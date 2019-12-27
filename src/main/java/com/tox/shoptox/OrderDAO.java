package com.tox.shoptox;

import java.util.*;

public class OrderDAO {

    final static private Map<Long, Order> orders =  new HashMap<Long, Order>();

    final static public Order createOrder(long productId){
       Order order = new Order();
       Product product = ProductDAO.get(productId);
       order.setProduct(product);
       order.setCount(1);
       order.setOrderDate(new Date());
       return order;
    }

    final static public void save(Order order) {
        HibernateUtil.getSessionFactory().getCurrentSession().save(order);
    }

    final static public Order get(long id){
        return (Order) HibernateUtil.getSessionFactory().getCurrentSession().createQuery("from Order as order where id = :id").setParameter("id", id).uniqueResult();
    };

    final static public List<Long> getOrders() {
        return HibernateUtil.getSessionFactory().getCurrentSession().createQuery("select id from Order").list();
    }

    final static public Long getOrdersCount(User user) {
        return (Long) HibernateUtil.getSessionFactory().getCurrentSession().createQuery("select count(*) from Order where user = :user").setParameter("user", user).uniqueResult();
    }

}