import models.Company;
import models.Identification;
import org.junit.*;

import play.data.Form;
import play.libs.F;
import play.test.TestBrowser;
import play.twirl.api.Content;

import java.util.ArrayList;

import static play.test.Helpers.*;
import static org.junit.Assert.*;


/**
*
* Simple (JUnit) tests that can call all parts of a play app.
* If you are interested in mocking a whole application, see the wiki for more details.
*
*/
public class ApplicationTest {

    @Test
    public void simpleCheck() {
        int a = 1 + 1;
        assertEquals(2, a);
    }

    @Test
    public void renderTemplate() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {
            Form<Identification> identificationForm = Form.form(Identification.class);
            Form<Company> companyForm = Form.form(Company.class);
            Content html = views.html.main.render(new ArrayList<>(), new ArrayList<>(), identificationForm, companyForm);
            assertEquals("text/html", contentType(html));
            assertTrue(contentAsString(html).contains("List of Identifications"));
        });

    }


}
