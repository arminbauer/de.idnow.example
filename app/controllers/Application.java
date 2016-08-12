package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

import java.util.Objects;

public class Application extends Controller {

    public Result index() {
        return ok(index.render("Your new application is ready."));
    }

}
