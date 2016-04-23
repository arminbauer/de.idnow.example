package controllers.handler;

import play.Logger;
import play.http.HttpErrorHandler;
import play.libs.F;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;
import services.exceptions.BadRequestException;

/**
 * Created by ebajrami on 4/23/16.
 */
public class ExceptionHandler implements HttpErrorHandler {

    @Override
    public F.Promise<play.mvc.Result> onClientError(Http.RequestHeader requestHeader, int i, String s) {
        return null;
    }

    @Override
    public F.Promise<play.mvc.Result> onServerError(Http.RequestHeader requestHeader, Throwable throwable) {
        Logger.error("Error", throwable);
        // for now just handle bad request
        if (throwable instanceof BadRequestException) {
            return F.Promise.<Result>pure(Results.badRequest());
        } else {
            return F.Promise.<Result>pure(Results.status(500));
        }
    }
}
