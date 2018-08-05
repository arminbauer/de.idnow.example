package controllers;

import models.Company;
import models.Identification;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import repositories.CompanyRepository;
import repositories.IdentificationRepository;
import views.html.main;

import javax.annotation.Nonnull;
import javax.inject.Inject;

@SuppressWarnings("unused")
public class Application extends Controller {
  private final IdentificationRepository identificationRepository;
  private final CompanyRepository companyRepository;

  @Inject
  public Application(@Nonnull final IdentificationRepository identificationRepository, @Nonnull final CompanyRepository companyRepository) {
    this.identificationRepository = identificationRepository;
    this.companyRepository = companyRepository;
  }

  public Result index() {
    return ok(main.render(identificationRepository.allPending(), companyRepository.all(), Form.form(Identification.class), Form.form(Company.class)));
  }

  public Result addCompany() {
    return null;
  }

  public Result startIdentification() {
    return null;
  }
}
