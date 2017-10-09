import org.junit.Test;
import play.twirl.api.Content;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.contentType;

public class ApplicationsTest {

    @Test
    public void simpleCheck() {
        int a = 1 + 1;
        assertEquals(2, a);
    }

    @Test
    public void renderTemplate() {
        Content html = views.html.index.render("Your new application is ready.");
        assertEquals("text/html", contentType(html));
        assertTrue(contentAsString(html).contains("Your new application is ready."));
    }
}
