package com.tox.shoptox;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

public class PurchasePanel extends Panel {

    private boolean checked = false;

    public PurchasePanel(String id, IModel<Long> model) {
        super(id, model);

        IModel<Order> orderModel = (IModel<Order>) Order::new;

        add(new Form<Order>("edit_form", orderModel){
            {
                add(new Component[]{(new NumberTextField<Integer>("count")).setRequired(true)});
            }
            @Override
            protected void onSubmit() {
                super.onSubmit();
            }
        });
    }

}
