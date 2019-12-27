package com.tox.shoptox;


import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.validation.FormComponentFeedbackBorder;
import org.apache.wicket.markup.html.image.ContextImage;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;
import org.apache.wicket.validation.validator.RangeValidator;
import org.springframework.util.DigestUtils;

import java.nio.ByteBuffer;
import java.util.List;

import static com.tox.shoptox.UserDAO.getUsersCount;

public class HomePage extends BasePage {
	private static final long serialVersionUID = 1L;


	Label cart;

	public HomePage(PageParameters parameters) {
		super(parameters);

		add(new FeedbackPanel("feedbackPanel"));


		add(new ListView<Long>("items", (IModel<List<Long>>) this::getProducts) {
			@Override
			protected void populateItem(ListItem<Long> listItem) {
				final IModel<Order> orderModel = new LoadableDetachableModel<Order>() {
					@Override
					protected Order load() {
						return OrderDAO.createOrder(listItem.getModelObject());
					}
				};
				listItem.add(new ContextImage("image", (IModel<String>)() -> ProductDAO.get(listItem.getModel().getObject()).getImage()));
				listItem.add(new Label("title", (IModel<String>)() -> ProductDAO.get(listItem.getModel().getObject()).getTitle()));
				listItem.add(new Label("description", (IModel<String>)() -> ProductDAO.get(listItem.getModel().getObject()).getDescription()));
				listItem.add(new Label("cost", (IModel<Double>)() -> ProductDAO.get(listItem.getModel().getObject()).getCost()));
				listItem.add(new OrderForm(orderModel));
			}
		});

		getSession().info("Welcome!");


	}

	private class OrderForm extends Form<IModel<Order>>{
		private IModel<Order> orderModel;
		private TextField count;
		private  OrderForm(IModel<Order> orderModel){
			super("edit_form",
					new CompoundPropertyModel<IModel<Order>>(orderModel));


			this.orderModel = orderModel;
			count = new NumberTextField<Integer>("count");
			count.setRequired(true);
			count.add((IValidator<Integer>) iValidatable -> {
				Integer count = iValidatable.getValue();
				if (count > orderModel.getObject().getProduct().getCount()) {
					ValidationError error = new ValidationError("Not available.");
					iValidatable.error(error);
					getRootForm().error(error);
				}
			});
			count.add(new RangeValidator<>(1,10));
			final MarkupContainer countFeedback = new FormComponentFeedbackBorder("countFeedback");
			add(countFeedback);
			countFeedback.add(count);

			SubmitLink button;

			button = new SubmitLink("submit", this){
				@Override
				public void onSubmit() {
					super.onSubmit();

					if (!HomePage.this.getSession().isAuthenticated()) {

						User user = UserLogic.registerNewUser();
						HomePage.this.getSession().logIn(user);
					}

					orderModel.getObject().setUser(HomePage.this.getSession().getUser());

					OrderLogic.processOrder(orderModel.getObject());

					getSession().success("Enjoy it!");
				}
				@Override
				public void onError(){
					super.onError();
					getSession().error("Not available!");
				}
			};

			add(button);

		}



	}

	public List<Long> getProducts() {
		return ProductDAO.getProducts();
	}
}
