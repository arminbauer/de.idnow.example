package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

import java.util.*;
import models.*;

public class Application extends Controller {


    public Result index() {
        //return ok(index.render("Your new application is ready."));
		return ok("Your new application is ready.");

    }

}
