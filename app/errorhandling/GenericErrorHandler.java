package errorhandling;

import play.Configuration;
import play.Environment;
import play.api.OptionalSourceMapper;
import play.api.routing.Router;
import play.http.DefaultHttpErrorHandler;
import play.libs.F;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;
import javax.validation.ValidationException;

/**
 * @author Dmitrii Bogdanov
 * Created at 04.08.18
 */
@Singleton
public class GenericErrorHandler extends DefaultHttpErrorHandler {

  @Inject
  public GenericErrorHandler(@Nonnull final Configuration configuration,
                             @Nonnull final Environment environment,
                             @Nonnull final OptionalSourceMapper optionalSourceMapper,
                             @Nonnull final Provider<Router> provider) {
    super(configuration, environment, optionalSourceMapper, provider);
  }

  @Override
  public F.Promise<Result> onServerError(@Nonnull final Http.RequestHeader requestHeader, @Nonnull final Throwable throwable) {
    if (throwable instanceof EntityNotFoundException) {
      return F.Promise.pure(Results.notFound(throwable.getMessage() + " not found"));
    } else if (throwable instanceof ValidationException || throwable instanceof PersistenceException) {
      return F.Promise.pure(Results.badRequest(throwable.getMessage()));
    } else {
      return super.onServerError(requestHeader, throwable);
    }
  }
}
