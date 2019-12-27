package com.tox.shoptox;

import java.util.List;

public class ProductDAO {

    static public void save(Product product) {
        HibernateUtil.getSessionFactory().getCurrentSession().save(product);
    }


    public static List<Long> getProducts(){
        return HibernateUtil.getSessionFactory().getCurrentSession().createQuery("select id from Product").list();
    }

    public static Product createProduct(){
        Product product = new Product();
        save(product);
        return product;
    }



    public static Product get(long id) {
        Product product = (Product) HibernateUtil.getSessionFactory().getCurrentSession().createQuery("from Product as product where id = :id").setParameter("id", id).uniqueResult();

        return product;
    }
}
