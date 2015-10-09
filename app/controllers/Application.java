package controllers;

import business.identification.entity.Identification;
import data.IdentificationTO;
import play.data.Form;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.addIdentification;
import views.html.index;

public class Application extends Controller {

    public Result index() {
        return ok(index.render("Your new application is ready."));
    }

    public Result getAddIdentification() {

        Form<IdentificationTO> identData = Form.form (IdentificationTO.class).fill (new IdentificationTO ());
        return ok(addIdentification.render (identData));
    }

    @Transactional
    public Result postAddIdentification() {

        Form<IdentificationTO> identData  = Form.form (IdentificationTO.class).bindFromRequest ();

        if (identData.hasErrors ()) {

            flash ("error", "Please correct the errors");
            return badRequest (addIdentification.render (identData));
        }

        IdentificationTO to = identData.get ();

        Identification identification = Identification.fromTO (to);

        JPA.em ().persist (identification);

        flash ("success", "Data saved successfully");
        return redirect (routes.Application.getAddIdentification ());
    }

}
