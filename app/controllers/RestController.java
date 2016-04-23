package controllers;

import com.google.inject.Inject;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import services.CompanyManagementService;
import services.IdentificationService;
import services.dto.CompanyDTO;
import services.dto.IdentificationDTO;
import services.exceptions.DuplicateIdentificationException;
import services.exceptions.InvalidCompanyException;

public class RestController extends Controller {

    @Inject
    CompanyManagementService companyManagementService;

    @Inject
    IdentificationService identificationService;

    private static <T> T fromRequestToObject(Class<T> type) {
        return Json.fromJson(request().body().asJson(), type);
    }

    /**
     * POST /api/v1/startIdentification: Here you can POST a identification object which is then added to the current
     * list of open identifications
     *
     * @return
     * @throws InvalidCompanyException
     * @throws DuplicateIdentificationException
     * @see IdentificationService
     */
    public Result startIdentification() throws InvalidCompanyException, DuplicateIdentificationException {
        //Do something with the identification
        identificationService.startIdentification(fromRequestToObject(IdentificationDTO.class));
        return ok();
    }

    /**
     * POST /api/v1/addCompany Adds new company to the app
     *
     * @return
     * @throws InvalidCompanyException
     * @see CompanyManagementService
     */
    public Result addCompany() throws InvalidCompanyException {
        //Get the parsed JSON data
        //Do something with the company
        CompanyDTO companyDTO = fromRequestToObject(CompanyDTO.class);
        companyManagementService.addCompany(companyDTO);
        return ok();
    }


    /**
     * GET /api/v1/pendingIdentifications: Here you can get a list of identifications.
     * The identifications should be ordered in the optimal order regarding the SLA of the company,
     * the waiting time of the ident and the current SLA percentage of that company. More urgent idents come first.
     *
     * @return
     * @see IdentificationService
     */
    public Result identifications() {
        return ok(Json.toJson(identificationService.getPendingIdentifications()));
    }


}
