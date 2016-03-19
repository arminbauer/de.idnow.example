import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static play.test.Helpers.*;

/**
 * @author Manuel Poppe.
 */
public class RestControllerTestRoutes extends RestControllerTest {

    @Test
    public void testGetCompaniesRoute() {
        play.mvc.Result result = route(fakeRequest(GET, "/api/v1/getCompanies"));
        assertNotNull(result);
    }

    @Test
    public void testGetIdentificationsRoute() {
        play.mvc.Result result = route(fakeRequest(GET, "/api/v1/getIdentifications"));
        assertNotNull(result);
    }

    @Test
    public void testidentificationsRoute() {
        play.mvc.Result result = route(fakeRequest(GET, "/api/v1/identifications"));
        assertNotNull(result);
    }

    @Test
    public void testAddCompanyRoute() {
        play.mvc.Result result = route(fakeRequest(POST, "/api/v1/addCompany"));
        assertNotNull(result);
    }

//to Test:
//    POST    /api/v1/addIdentification   controllers.RestController.addIdentification()

}
