package com.tox.shoptox;

import org.apache.wicket.authroles.authentication.panel.SignInPanel;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public final class SignInPage extends BasePage
{
    /**
     * Constructor
     */
    public SignInPage()
    {
        this(null);
    }

    /**
     * Constructor
     *
     * @param parameters
     *            The page parameters
     */
    public SignInPage(final PageParameters parameters)
    {
        super(parameters);
        add(new SignInPanel("signInPanel", false){
            @Override
            protected void onSignInSucceeded() {
                setResponsePage(ProductsPage.class);
            }
        });
    }
}
