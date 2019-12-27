package com.tox.shoptox;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.validation.FormComponentFeedbackBorder;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.validation.validator.RangeValidator;
import org.apache.wicket.validation.validator.StringValidator;

public class ProductAddPage extends AuthenticatedWebPage {


    public ProductAddPage(PageParameters parameters) {
        super(parameters);

//        Course course = getSession().getUser().getCourse();
        add(new EditProductForm("productForm", (IModel<Product>) ProductDAO::createProduct));

    }

    static public final class EditProductForm extends Form<IModel<Product>> {

        private IModel<Product> product;
        public EditProductForm(String id, final IModel<Product> product){
            super(id, new CompoundPropertyModel<IModel<Product>>(product));


            this.product = product;

            final TextField<String> title = new TextField<>("title");
            title.setRequired(true);
            title.add(new StringValidator(null, 30));

            final MarkupContainer titleFeedback = new FormComponentFeedbackBorder("titleFeedback");
            add(titleFeedback);
            titleFeedback.add(title);

            final TextField<String> image = new TextField<>("image");
            image.setRequired(true);
            image.add(new StringValidator(null, 200));

            final MarkupContainer imageFeedback = new FormComponentFeedbackBorder("imageFeedback");
            add(imageFeedback);
            imageFeedback.add(image);


            final TextArea<String> description = new TextArea<>("description");
            description.setRequired(true);
            description.add(new StringValidator(null, 999));

            final MarkupContainer descriptionFeedback = new FormComponentFeedbackBorder("descriptionFeedback");
            add(descriptionFeedback);
            descriptionFeedback.add(description);

            // Create a required text field that edits the book's author
            final NumberTextField<Double> rating = new NumberTextField<>("rating");
            rating.setRequired(true);
            rating.add(new RangeValidator<>(1.0,5.0));
            final MarkupContainer ratingFeedback = new FormComponentFeedbackBorder("ratingFeedback");
            add(ratingFeedback);
            ratingFeedback.add(rating);

            // Create a required text field that edits the book's author
            final NumberTextField<Integer> count = new NumberTextField<>("count");
            count.setRequired(true);
            count.add(new RangeValidator<>(1,999));
            final MarkupContainer countFeedback = new FormComponentFeedbackBorder("countFeedback");
            add(countFeedback);
            countFeedback.add(count);

            // Create a required text field that edits the book's author
            final NumberTextField<Double> cost = new NumberTextField<>("cost");
            cost.setRequired(true);
            cost.add(new RangeValidator<>(1.0,999.0));
            final MarkupContainer costFeedback = new FormComponentFeedbackBorder("costFeedback");
            add(costFeedback);
            costFeedback.add(cost);
        }

        @Override
        protected void onSubmit() {
            super.onSubmit();
                setResponsePage(ProductPage.class, new PageParameters().add("id", product.getObject().getId()));
        }
    }

}
