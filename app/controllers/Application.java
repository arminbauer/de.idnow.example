package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

class Application extends Controller {

    public Result index() {
        return ok(index.render("Your new application is ready."));
    }

}
