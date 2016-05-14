package controllers;

import com.google.inject.Inject;
import dao.CompanyStore;
import play.mvc.Controller;
import play.mvc.Result;
import services.IdentificationsQueue;
import views.html.index;

import javax.inject.Singleton;

@Singleton
public class Application extends Controller {

    private final IdentificationsQueue queue;
    private final CompanyStore companyStore;

    @Inject
    public Application(IdentificationsQueue queue, CompanyStore companyStore) {
        this.queue = queue;
        this.companyStore = companyStore;
    }

    public Result index() {
        return ok(index.render(companyStore.findAll(), queue.getAllIdentifications()));
    }

}
