import org.junit.Test;
import play.twirl.api.Content;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static play.test.Helpers.contentAsString;


public class ApplicationTest {
  @Test
  public void renderTemplate() {
    final Content html = views.html.index.render("Your new application is ready.");
    assertEquals("text/html", html.contentType());
    assertTrue(contentAsString(html).contains("Your new application is ready."));
  }
}
