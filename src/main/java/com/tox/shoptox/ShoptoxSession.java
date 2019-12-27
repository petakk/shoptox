package com.tox.shoptox;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.request.Request;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.springframework.util.DigestUtils;

import java.util.List;

public class ShoptoxSession extends AuthenticatedWebSession
{

    private static Logger logger = Logger.getLogger(ShoptoxSession.class);

    // Logged in user
    static public User user;

    /**
     * Constructor
     *
     * @param request
     *            The current request object
     */
    protected ShoptoxSession(Request request)
    {
        super(request);
    }

    /**
     * Checks the given username and password, returning a User object if if the username and
     * password identify a valid user.
     *
     * @param username
     *            The username
     * @param password
     *            The password
     * @return The signed in user
     */
    @Override
    public final boolean authenticate(final String username, final String password) {
        if (!User.ADMIN.equals(username)) {
            return false;
        }

        if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)) {
            User user = UserDAO.getUser(username);
            if (user == null) {
                user = UserDAO.createUser(username,  DigestUtils.md5DigestAsHex(password.trim().toLowerCase().getBytes()));
                UserDAO.save(user);
            }
            if (user.isRightPassword(password)) {
                logIn(user);
                return true;
            }
        }

        return false;
    }

    public boolean isAuthenticated(){
        return getUser() != null;
    }

    /**
     * @return User
     */
    public User getUser()
    {
        return user;
    }

    /**
     * @param user
     *            New user
     */
    public void setUser(final User user)
    {
        this.user = user;
    }

    @Override
    public void invalidate()
    {
        setUser(null);
        super.invalidate();
    }

    @Override
    public Roles getRoles()
    {
        return null;
    }


    public final void logIn(User user) {
       super.signIn(true);
       setUser(user);

        logger.info(String.format("User#%d logged in.", user.getId()));
    }

}
