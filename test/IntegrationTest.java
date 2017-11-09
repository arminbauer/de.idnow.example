import static org.junit.Assert.assertTrue;
import org.junit.Test;
import static play.test.Helpers.HTMLUNIT;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;

public class IntegrationTest {

    /**
     * add your integration test here
     * in this example we just check if the welcome page is being shown
     *
     * Does neither work in IDEA, nor with activator.
     * Reason: 'http://localhost:3333/assets/javascripts/hello.js' cannot be downloaded (status 404).
     *
     * Solutions recommended in https://github.com/playframework/playframework/issues/3234 do not work either.
     */
    @Test
    public void test() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT, browser -> {
            browser.goTo("http://localhost:3333");
            assertTrue(browser.pageSource().contains("Your new application is ready."));
        });
    }

}
