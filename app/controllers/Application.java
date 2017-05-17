package controllers;

import play.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;


import views.html.*;

import models.State;
import models.Company;
import models.Identification;

import java.util.Map;

import javax.inject.*;

public class Application extends Controller {

    @Inject State state;

    public Result index() {
        return ok(index.render());
    }

    public Result addCompany() {
        Form<Company> form = form(Company.class);

        return ok(addCompany.render(form));
    }

    public Result saveCompany() {
        Form<Company> form = form(Company.class).bindFromRequest();

        if(form.hasErrors()) {
            return badRequest(addCompany.render(form));
        }

        Company company = form.get();
        state.getCompanies().put(company.getId(), company);
        flash("company-success", "Company " + company.getName() + " has been created");

        return redirect(controllers.routes.Application.index());
    }

    public Result addIdentification() {
        Form<Identification> form = form(Identification.class);

        return ok(addIdentification.render(form));
    }

    public Result saveIdentification() {
        Form<Identification> form = form(Identification.class).bindFromRequest();

        if(form.hasErrors()) {
            return badRequest(addIdentification.render(form));
        }

        Identification identification = form.get();
        identification.setTime(System.currentTimeMillis());

        Map<Integer, Company> companies = state.getCompanies();

        if (companies.get((Integer)identification.getCompanyid()) == null) {
            return badRequest(addIdentification.render(form));
        }

        state.getIdentifications().add(identification);

        flash("identification-success", "Identification " + identification.getName() + " has been created");

        return redirect(controllers.routes.Application.index());
    }
}
