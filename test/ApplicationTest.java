import com.google.common.collect.Lists;
import models.Company;
import models.Identification;
import org.hamcrest.Matchers;
import org.junit.Test;
import play.twirl.api.Content;

import static org.junit.Assert.assertThat;
import static play.test.Helpers.contentAsString;


/**
 * Simple (JUnit) tests that can call all parts of a play app.
 * If you are interested in mocking a whole application, see the wiki for more details.
 */
public class ApplicationTest {


    @Test
    public void renderTemplate() {
        Company company = RandomObjects.randomCompany();
        Identification ident = RandomObjects.randomIdentification();
        Content html = views.html.index.render(Lists.newArrayList(company), Lists.newArrayList(ident));
        assertThat(html.contentType(), Matchers.is("text/html"));

        assertThat(contentAsString(html), Matchers.containsString(String.valueOf(company.getId())));
        assertThat(contentAsString(html), Matchers.containsString(company.getName()));
        assertThat(contentAsString(html), Matchers.containsString(String.valueOf(company.getCurrentSlaPercentage())));
        assertThat(contentAsString(html), Matchers.containsString(String.valueOf(company.getSlaPercentage())));
        assertThat(contentAsString(html), Matchers.containsString(String.valueOf(company.getSlaTimeSeconds())));
    }


}
