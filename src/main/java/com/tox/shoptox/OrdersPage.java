package com.tox.shoptox;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.util.List;

public class OrdersPage extends AuthenticatedWebPage {

    public OrdersPage(PageParameters parameters) {
        super(parameters);

        final PageableListView<Long> listView;

        add(listView = new PageableListView<Long>("orders", new PropertyModel<>(this,
                "orders"), 40)
        {

            @Override
            public void populateItem(final ListItem<Long> listItem)
            {
                long id = listItem.getModelObject();

                IModel<Order> orderModel = (IModel<Order>)() -> OrderDAO.get(id);

                listItem.add(new BookmarkablePageLink<Void>("details", OrderPage.class, new PageParameters().add("id", orderModel.getObject().getId())).add(new Label("order", new PropertyModel<>(orderModel, "id"))));
                listItem.add(new Label("product", new PropertyModel(orderModel, "product.title")));
                listItem.add(new Label("count", new PropertyModel(orderModel, "count")));
                listItem.add(new Label("date", new PropertyModel(orderModel, "orderDate")));
                listItem.add(new Label("price", new PropertyModel(orderModel, "price")));
                listItem.add(new Label("user", new PropertyModel(orderModel, "user.name")));

            }
        });
    }

    public List<Long> getOrders()
    {
        // Note: checkAccess() (and thus login etc.) happen after the Page
        // has been instantiated. Thus, you can not realy on user != null.
        // Note2: In any case, all components must be associated with a
        // wicket tag.

        return OrderDAO.getOrders();
    }

}
