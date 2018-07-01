package util.exceptions;

import play.*;
import play.api.OptionalSourceMapper;
import play.api.routing.Router;
import play.http.DefaultHttpErrorHandler;
import play.libs.F.*;
import play.mvc.Http.*;
import play.mvc.*;

import javax.inject.*;
import javax.persistence.PersistenceException;

public class ErrorHandler extends DefaultHttpErrorHandler {

    @Inject
    public ErrorHandler(Configuration configuration, Environment environment,
                        OptionalSourceMapper sourceMapper, Provider<Router> routes) {
        super(configuration, environment, sourceMapper, routes);
    }

    @Override
    public Promise<Result> onServerError(RequestHeader request, Throwable exception) {
        // This type of exception handling is made only for exceptions that we expect
        if (exception instanceof IllegalArgumentException || exception instanceof PersistenceException) {
            return Promise.pure(Results.badRequest(exception.getMessage()));
        }
        // For unexpected exceptions we send response with other error code
        return super.onServerError(request, exception);
    }
}