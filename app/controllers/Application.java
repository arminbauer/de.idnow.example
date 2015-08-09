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

        if (!request().uri().contains("test")){
            // init with no data
            DBEmulator.getInstance().init(0);
        }

        return ok(index.render(
                        DBEmulator.getInstance().getCompanyList(),
                        DBEmulator.getInstance().getIdentificationList(),
                        IdentificationService.getInstance().computeOrder()
                        )
                );
    }

    // for testing purpose:

    public Result test1() {

        // init with test 1 data
        DBEmulator.getInstance().init(1);

        return ok(index.render(
                        DBEmulator.getInstance().getCompanyList(),
                        DBEmulator.getInstance().getIdentificationList(),
                        IdentificationService.getInstance().computeOrder()
                )
        );
    }

    public Result test2() {

        // init with test 2 data
        DBEmulator.getInstance().init(2);

        return ok(index.render(
                        DBEmulator.getInstance().getCompanyList(),
                        DBEmulator.getInstance().getIdentificationList(),
                        IdentificationService.getInstance().computeOrder()
                )
        );
    }

    public Result test3() {

        // init with test 3 data
        DBEmulator.getInstance().init(3);

        return ok(index.render(
                        DBEmulator.getInstance().getCompanyList(),
                        DBEmulator.getInstance().getIdentificationList(),
                        IdentificationService.getInstance().computeOrder()
                )
        );
    }

    public Result test4() {

        // init with test 4 data
        DBEmulator.getInstance().init(4);

        return ok(index.render(
                        DBEmulator.getInstance().getCompanyList(),
                        DBEmulator.getInstance().getIdentificationList(),
                        IdentificationService.getInstance().computeOrder()
                )
        );
    }

}
