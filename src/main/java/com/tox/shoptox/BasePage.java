package com.tox.shoptox;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class BasePage extends WebPage {

    public BasePage() {
        super();
        add(new BookmarkablePageLink<Void>("singOut", SignOutPage.class));

        add(new WebMarkupContainer("adminMenu"));

        add( new Label("cart", (IModel<Integer>)() -> {
            if (getSession().isSignedIn()) {
                return (int) getSession().getUser().getCount();
            } else {
                return 0;
            }
        }));


    }

    public BasePage(PageParameters parameters) {
        super(parameters);
        add(new BookmarkablePageLink<Void>("singOut", SignOutPage.class));
        add(new WebMarkupContainer("adminMenu"));
        add( new Label("cart", (IModel<Integer>)() -> {
            if (getSession().isSignedIn()) {
                return (int) getSession().getUser().getCount();
            } else {
                return 0;
            }
        }));
    }


    public void onBeforeRender() {
        super.onBeforeRender();
        get("cart").setVisible(getSession().isSignedIn());
        get("singOut").setVisible(getSession().isSignedIn());
        get("adminMenu").setVisible(getSession().isSignedIn() && getSession().getUser().isAdmin());
    }

    /**
     * Get downcast session object
     *
     * @return The session
     */
    @Override
    public ShoptoxSession getSession()
    {
        return (ShoptoxSession)super.getSession();
    }
}
