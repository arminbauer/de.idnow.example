package errorhandling;

import play.http.HttpErrorHandler;
import play.libs.F;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;

import javax.validation.ValidationException;

public class ErrorHandler implements HttpErrorHandler {

	@Override
	public F.Promise<Result> onClientError(Http.RequestHeader request, int statusCode, String message) {
		return null;
	}

	@Override
	public F.Promise<Result> onServerError(Http.RequestHeader request, Throwable exception) {
		if (exception instanceof ValidationException) {
			return error(Results.badRequest(exception.getMessage()));
		}
		if (exception instanceof NotFoundException) {
			return error(Results.notFound(exception.getMessage()));
		}
		return error(Results.internalServerError(exception.getMessage()));
	}

	private F.Promise<Result> error(Results.Status status) {
		return F.Promise.<Result>pure(status);
	}
}
