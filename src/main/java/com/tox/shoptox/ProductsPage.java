package com.tox.shoptox;

import org.apache.log4j.Logger;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.util.List;

public class ProductsPage extends AuthenticatedWebPage {


    private static Logger logger = Logger.getLogger(WicketApplication.class);


    public void initProducts(){

        if (ProductDAO.getProducts().size() == 0) {

            Product product = new Product();
            product.setTitle("Ad Astra");
            product.setDescription("2019 Drama 125 minutes");
            product.setCost(10.0);
            product.setCount(10);
            product.setRating(4.5);
            ProductDAO.save(product);

            product = new Product();
            product.setTitle("Текст");
            product.setDescription("2019 Drama 131 minutes");
            product.setCost(9.0);
            product.setCount(10);
            product.setRating(4.5);
            ProductDAO.save(product);

            product = new Product();
            product.setTitle("Код 8");
            product.setDescription("2019 Drama 94 minutes");
            product.setCost(7.5);
            product.setCount(2);
            product.setRating(4.5);
            ProductDAO.save(product);

            product = new Product();
            product.setTitle("Во всё тяжкое");
            product.setDescription("2019 Drama 86 minutes");
            product.setCost(9.5);
            product.setCount(11);
            product.setRating(4.5);
            ProductDAO.save(product);

            product = new Product();
            product.setTitle("Комната желаний");
            product.setDescription("2019 Crime 95 minutes");
            product.setCost(10.0);
            product.setCount(10);
            product.setRating(4.5);
            ProductDAO.save(product);

        }

    }

    public ProductsPage(final PageParameters parameters)
    {
        // Add table of books
        super(parameters);
        final PageableListView<Long> listView;

        initProducts();

        add(listView = new PageableListView<Long>("products", new PropertyModel<>(this,
                "products"), 40)
        {

            @Override
            public void populateItem(final ListItem<Long> listItem)
            {
                long id = listItem.getModelObject();

                IModel<Product> productModel = (IModel<Product>)() -> ProductDAO.get(id);

                listItem.add(new BookmarkablePageLink<Void>("details", ProductPage.class, new PageParameters().add("id", productModel.getObject().getId())).add(new Label("product", new PropertyModel<>(productModel, "title"))));
                listItem.add(new Label("description", new PropertyModel(productModel, "description")));
                listItem.add(new Label("cost", new PropertyModel(productModel, "cost")));
                listItem.add(new Label("count", new PropertyModel(productModel, "count")));
            }
        });


    }

    /**
     *
     * @return List of golfers
     */
    public List<Long> getProducts()
    {
        // Note: checkAccess() (and thus login etc.) happen after the Page
        // has been instantiated. Thus, you can not realy on user != null.
        // Note2: In any case, all components must be associated with a
        // wicket tag.

        return ProductDAO.getProducts();
    }

}
