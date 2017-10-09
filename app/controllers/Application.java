package controllers;

import models.IdentificationTO;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

import static play.data.Form.form;

public class Application extends Controller {


    public Result index() {

        Form<IdentificationTO> form = form(IdentificationTO.class);

        return ok(index.render(form));
    }

}
