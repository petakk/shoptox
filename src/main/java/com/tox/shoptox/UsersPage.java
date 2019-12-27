package com.tox.shoptox;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.util.List;

public class UsersPage extends AuthenticatedWebPage {
    public UsersPage(PageParameters parameters) {
        super(parameters);

        add(new ListView<Long>("users", new LoadableDetachableModel<List<Long>>() {
            @Override
            protected List<Long> load() {
                return UserDAO.getUsers();
            }
        }) {
            @Override
            protected void populateItem(ListItem<Long> listItem) {

                IModel<User> user = (IModel<User>) () -> UserDAO.get(listItem.getModel().getObject());
                listItem.add(new Link<Void>("details") {
                    @Override
                    public void onClick() {
                        setResponsePage(UserPage.class, new PageParameters().add("id", user.getObject().getId()));
                    }
                }.add(new Label("userId", new PropertyModel<>(user, "id"))));
                listItem.add(new Label("name", new PropertyModel<>(user, "name")));
                listItem.add(new Label("count", new PropertyModel<>(user, "count")));


            }
        });


    }
}
