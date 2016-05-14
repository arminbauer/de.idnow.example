import controllers.Errors;
import play.http.HttpErrorHandler;
import play.libs.F;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;

/**
 * Created by nick on 14.05.16.
 */
public class ErrorHandler implements HttpErrorHandler {

    public F.Promise<Result> onClientError(Http.RequestHeader request, int statusCode, String message) {
        return F.Promise.<Result>pure(
                Results.status(statusCode, Errors.error(message))
        );
    }

    public F.Promise<Result> onServerError(Http.RequestHeader request, Throwable exception) {
        return F.Promise.<Result>pure(
                Results.internalServerError(Errors.error(exception.getMessage()))
        );
    }
}