package com.tox.shoptox;

import org.apache.wicket.RestartResponseException;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class SignOutPage extends BasePage
{
    /**
     * Constructor
     *
     * @param parameters
     *            Page parameters (ignored since this is the home page)
     */
    public SignOutPage(final PageParameters parameters)
    {
        getSession().invalidate();

        setResponsePage(HomePage.class);

    }
}
