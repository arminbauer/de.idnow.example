import org.junit.Test;
import play.mvc.Result;
import play.test.WithApplication;
import play.twirl.api.Content;

import static org.junit.Assert.*;
import static play.test.Helpers.*;


/**
 * Simple (JUnit) tests that can call all parts of a play app.
 * If you are interested in mocking a whole application, see the wiki for more details.
 */
public class ApplicationTest extends WithApplication {

    @Test
    public void simpleCheck() {
        int a = 1 + 1;
        assertEquals(2, a);
    }

    @Test
    public void renderTemplate() {
        Content html = views.html.index.render("Add a new identification here:");
        assertEquals("text/html", contentType(html));
        assertTrue(contentAsString(html).contains("Add a new identification here:"));
    }


}
