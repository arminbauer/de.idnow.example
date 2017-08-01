package controllers;

import javax.inject.Inject;

import manager.IdentificationManagerImpl;
import mapper.IdentificationMapper;
import models.Identification;
import models.IdentificationDTO;
import play.*;
import play.Logger.ALogger;
import play.data.Form;
import play.mvc.*;

import views.html.*;

/**
 * Displays the index page.
 *
 * @author Markus Panholzer <markus.panholzer@eforce21.com>
 * @since 31.09.2017
 */
public class Application extends Controller {

	public Result index() {
		return ok(index.render("Your new application is ready."));
	}

}
