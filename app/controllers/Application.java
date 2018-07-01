package controllers;

import models.Company;
import models.Identification;
import play.data.Form;
import play.mvc.*;

import service.CompanyService;
import service.IdentificationService;
import views.html.*;

import javax.inject.Inject;

public class Application extends Controller {

    @Inject
    private IdentificationService identificationService;

    @Inject
    private CompanyService companyService;

    public Result main() {
        Form<Identification> identificationForm = Form.form(Identification.class);
        Form<Company> companyForm = Form.form(Company.class);
        return ok(main.render(
                identificationService.getIdentificationsOrderBySLA(),
                companyService.getAll(),
                identificationForm,
                companyForm));
    }

    public Result startIdentification() {
        Form<Identification> identificationForm = Form.form(Identification.class).bindFromRequest();
        Identification identification = identificationForm.get();
        identificationService.save(identification);
        return main();
    }

    public Result addCompany() {
        Form<Company> companyForm = Form.form(Company.class).bindFromRequest();
        Company company = companyForm.get();
        companyService.save(company);
        return main();
    }
}
