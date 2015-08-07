package controllers;

import models.Company;
import play.*;
import play.mvc.*;

import util.DBEmulator;
import util.IdentificationService;
import views.html.*;

import java.util.ArrayList;
import java.util.List;

public class Application extends Controller {

    public Result index() {

        return ok(index.render(
                        DBEmulator.getInstance().getCompanyList(),
                        DBEmulator.getInstance().getIdentificationList(),
                        IdentificationService.getInstance().computeOrder()
                        )
                );
    }

}
