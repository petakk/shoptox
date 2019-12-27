package com.tox.shoptox;

import org.apache.commons.lang3.StringUtils;


import org.hibernate.annotations.GenericGenerator;
import org.springframework.util.DigestUtils;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


public final class User
{

    final static public String ADMIN = "admin";

    private Long id;
    private String name;
    private String password;

    public User() {

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return User name
     */
    public final String getName()
    {
        return name;
    }

    /**
     * @param string
     *            User name
     */
    public final void setName(final String string)
    {
        name = string;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public boolean isRightPassword(String password) {
        return (StringUtils.isNotBlank(password)
                && StringUtils.isNotBlank(getPassword())
                && (DigestUtils.md5DigestAsHex(password.trim().toLowerCase().getBytes()).equals(getPassword())));
    }

    public boolean isAdmin() {
        return name.equals(ADMIN);
    }

    public long getCount() {
        return OrderDAO.getOrdersCount(this);
    }

}