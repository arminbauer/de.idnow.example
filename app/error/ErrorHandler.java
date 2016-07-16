package error;

import com.google.inject.Inject;
import play.Configuration;
import play.Environment;
import play.api.OptionalSourceMapper;
import play.api.routing.Router;
import play.http.DefaultHttpErrorHandler;
import play.mvc.*;
import play.mvc.Http.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import javax.inject.Provider;
import javax.inject.Singleton;

/**
 * ErrorHandler for handling the exceptions and sending the appropriate responses
 */
@Singleton
public class ErrorHandler extends DefaultHttpErrorHandler {

    @Inject
    public ErrorHandler(Configuration configuration, Environment environment, OptionalSourceMapper sourceMapper, Provider<Router> routes) {
        super(configuration, environment, sourceMapper, routes);
    }

    /**
     * In case of an error thrown by the application, send 500 back.
     * Request validation errors are handled by the service and controller layers.
     *
     * @param request
     * @param exception
     * @return
     */
    public CompletionStage<Result> onServerError(RequestHeader request, Throwable exception) {
        return CompletableFuture.completedFuture(
                Results.internalServerError(exception.getMessage())
        );
    }
}
