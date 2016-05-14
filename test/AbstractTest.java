import com.google.inject.Inject;
import org.apache.commons.lang3.RandomUtils;
import org.junit.After;
import org.junit.Before;
import play.Application;
import play.libs.ws.WS;
import play.libs.ws.WSRequest;
import play.test.Helpers;

/**
 * Created by nick on 14.05.16.
 */
abstract class AbstractTest {

    @Inject
    protected Application application;

    protected int port;

    @Before
    public void pickRandomPort() throws Exception {
        port = RandomUtils.nextInt(32000, 64000);
    }

    protected WSRequest localUrl(String suffix) {
        return WS.url("http://localhost:" + port + suffix);
    }

    @After
    public void teardown() {
        Helpers.stop(application);
    }
}
