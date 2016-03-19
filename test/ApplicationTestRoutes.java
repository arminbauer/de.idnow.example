import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static play.test.Helpers.GET;
import static play.test.Helpers.fakeRequest;
import static play.test.Helpers.route;

/**
 * @author Manuel Poppe.
 */
public class ApplicationTestRoutes extends ApplicationTest {

    @Test
    public void testIndexRoute() {
        play.mvc.Result result = route(fakeRequest(GET, "/"));
        assertNotNull(result);
    }

}
