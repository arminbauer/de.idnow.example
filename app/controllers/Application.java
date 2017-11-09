package controllers;

import models.CompanyJSON;
import models.CompanyStore;
import models.DataStoreException;
import models.IdentificationJSON;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.company;
import views.html.identification;
import views.html.index;

import java.util.UUID;

public class Application extends Controller {
    public Result index() {
        return ok(index.render("Your new application is ready."));
    }

    public Result identification() {
        try {
            CompanyStore.initialize();
        } catch(DataStoreException ex) {
            return internalServerError(ex.getMessage());
        }

        IdentificationJSON identificationJSON = new IdentificationJSON();
        identificationJSON.setId(UUID.randomUUID().toString());
        Form<IdentificationJSON> form = Form.form(IdentificationJSON.class);
        return ok(identification.render(form.fill(identificationJSON)));
    }

    public Result company() {
        CompanyJSON companyJSON = new CompanyJSON();
        companyJSON.setId(UUID.randomUUID().toString());
        Form<CompanyJSON> form = Form.form(CompanyJSON.class);
        return ok(company.render(form.fill(companyJSON)));
    }

}
