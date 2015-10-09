import org.junit.*;

import org.openqa.selenium.WebDriver;
import play.Logger;
import play.mvc.*;
import play.test.*;
import play.libs.F.*;

import static play.test.Helpers.*;
import static org.junit.Assert.*;

import static org.fluentlenium.core.filter.FilterConstructor.*;

public class IntegrationTest {

    /**
     * add your integration test here
     * in this example we just check if the welcome page is being shown
     */
    @Test
    public void test() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) {

                try {
                    browser.goTo("http://localhost:3333");
                } catch (Exception e) {
                    Logger.warn ( "Fails due to jQuery, seems to be an issue with htmlunit. " +
                                  "Browser renders without problem: Error=" + e.getMessage () );
                }

                String content = browser.pageSource ();
                Logger.info ( "Content: " + content );
                assertTrue (content.contains ( "Welcome to Play" ) );
            }
        });
    }

}
