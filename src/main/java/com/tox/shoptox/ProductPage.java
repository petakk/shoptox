package com.tox.shoptox;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.ContextImage;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class ProductPage extends AuthenticatedWebPage {

    public ProductPage(PageParameters parameters) {
        super(parameters);

        long id = parameters.get("id").toLong(0);

        IModel<Product> productIModel = (IModel<Product>) () -> ProductDAO.get(id);

        setDefaultModel(new CompoundPropertyModel<>(productIModel));

        add(new Label("title"));
        add(new Label("description"));
        add(new Label("rating"));
        add(new ContextImage("image", new PropertyModel<>(productIModel, "image")));
        add(new Label("count"));
        add(new Label("cost"));

    }
}
