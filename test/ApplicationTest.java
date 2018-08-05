import models.Company;
import models.Identification;
import org.junit.Test;
import play.data.Form;
import play.db.Database;
import play.db.evolutions.Evolutions;
import play.test.FakeApplication;
import play.test.Helpers;
import play.twirl.api.Content;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static play.test.Helpers.*;


public class ApplicationTest {
  @Test
  public void renderTemplate() {
    final FakeApplication app = Helpers.fakeApplication();
    final Database db = app.injector().instanceOf(Database.class);
    running(testServer(3333, app), () -> {
      final Content html = views.html.main.render(Collections.emptyList(), Collections.emptyList(), Form.form(Identification.class), Form.form(Company.class));
      assertEquals("text/html", html.contentType());
      assertTrue(contentAsString(html).contains("Identifications"));
      assertTrue(contentAsString(html).contains("Companies"));
      Evolutions.cleanupEvolutions(db);
    });
  }
}
