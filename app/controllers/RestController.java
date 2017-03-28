package controllers;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.core.JsonProcessingException;
import models.Company;
import models.Identification;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import util.Utility;

import java.util.Collections;
import java.util.List;

import static play.libs.Json.toJson;

public class RestController extends Controller {

    public Result startIdentification() throws JsonProcessingException {
        Identification identification = Form.form(Identification.class).bindFromRequest().get();
        identification.save();
        return redirect(routes.Application.index());
    }

    public Result addCompany() {
        Company company = Form.form(Company.class).bindFromRequest().get();
        company.save();
        return redirect(routes.Application.index());
    }

    public Result getCompanies() {
        List<Company> companies = new Model.Finder(Company.class).all();
        return ok(toJson(companies));
    }

    public Result identifications() {
        List<Identification> identifications = new Model.Finder(Identification.class).all();
        return ok(toJson(identifications));
    }

    public Result pendingIdentifications(){
        List<Identification> identifications = new Model.Finder(Identification.class).all();
        List<Company> companies = new Model.Finder(Company.class).all();
        Utility.setCompaniesForeignKeys(identifications, companies);
        Collections.sort(identifications);
        return ok(toJson(identifications));
    }

}
