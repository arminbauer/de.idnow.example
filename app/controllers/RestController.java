package controllers;

import static akka.pattern.Patterns.ask;

import java.util.List;

import models.Company;
import models.Identification;
import play.Logger;
import play.libs.F.Promise;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import services.CompanyService;
import actors.GuiceInjectedActor;
import actors.QueueActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.POJONode;
import com.google.inject.Inject;
import com.google.inject.Injector;

public class RestController extends Controller {

    private final static Logger.ALogger logger = Logger.of(RestController.class);

    private final ActorRef queueActor;

    private final CompanyService companyService;

    @Inject
    public RestController(ActorSystem system, Injector injector, CompanyService companyService) {
        this.queueActor = system.actorOf(Props.create(GuiceInjectedActor.class, injector, QueueActor.class));
        this.companyService = companyService;
    }

    /**
     * Here you can POST a identification object which is then added to the current list of open
     * identifications
     * 
     * @return
     */
    @BodyParser.Of(BodyParser.Json.class)
    public Result startIdentification() {
        JsonNode json = request().body().asJson();
        Identification id = Json.fromJson(json, Identification.class);
        ask(queueActor, new QueueActor.Append(id), 1000);
        return ok();
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result addCompany() {
        //Get the parsed JSON data
        JsonNode json = request().body().asJson();
        //Do something with the company
        Company company = Json.fromJson(json, Company.class);
        companyService.addCompany(company);
        return ok();
    }

    /**
     * Here you can get a list of identifications. The identifications should be ordered in the
     * optimal order regarding the SLA of the company, the waiting time of the ident and the current
     * SLA percentage of that company. More urgent idents come first
     * 
     * @return
     */
    public Promise<Result> identifications() {
        //Get the current identification
        //Compute correct order
        //Create new identification JSON with JsonNode identification = Json.newObject();
        //Add identification to identifications list
        return Promise.wrap(ask(queueActor, new QueueActor.Retrieve(), 1000)). //
                map(ids -> {
                    ArrayNode identifications = Json.newArray();
                    ((List<Identification>) ids).stream().forEach(id -> identifications.add(new POJONode(id)));
                    return ok(identifications);
                });
    }

}
