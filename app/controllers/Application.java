package controllers;

import models.Identification;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.ident_form;
import views.html.index;

import java.util.UUID;

public class Application extends Controller {

    public Result index() {
        return ok(index.render("Your new application is ready."));
    }

    public Result ident_form() {
        Form<Identification> form = Form.form(Identification.class);

        Identification ident = new Identification(UUID.randomUUID(), "Name", System.currentTimeMillis() / 1000, 45L, UUID.randomUUID());
        form.fill(ident);

        return ok(ident_form.render(form));
    }

}
