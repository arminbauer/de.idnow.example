package controllers;

import com.google.inject.Inject;
import models.Identification;
import play.api.mvc.Codec;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import services.IdentificationsQueue;
import services.IdentificationsQueueDuplicateException;

import static controllers.Errors.validationError;

public class IdentificationsController extends Controller {

    private final IdentificationsQueue queue;


    @Inject
    public IdentificationsController(IdentificationsQueue queue) {
        this.queue = queue;
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result startIdentification() {
        ValidJson<Identification> json = new ValidJson<>(request().body().asJson(), Identification.class);
        if (json.hasErrors()) {
            return badRequest(validationError(json.getErrors()));
        }
        try {
            queue.add(json.get());
            return noContent();
        } catch (IdentificationsQueueDuplicateException e) {

            return new Status(play.core.j.JavaResults.Conflict(), Errors.error(e.getMessage()), Codec.javaSupported("utf-8"));

        }
    }


    public Result getAllIdentifications() {
        return ok(Json.toJson(queue.getAllIdentifications()));
    }

}
