package components;


import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

public class EditStringForm extends Panel {


    private boolean checked = false;

    public EditStringForm(String id, IModel<String> model) {
        super(id, model);

        add(new CheckBox("editable__trigger", new PropertyModel<>(this, "checked")));

        add(new Label("editable_string", model));

        add(new Form<String>("edit_form", model){
            {
                add(new Component[]{(new TextField("string", getModel())).setRequired(true)});
            }
            @Override
            protected void onSubmit() {
                super.onSubmit();
            }
        });
    }
}
