import data.AppData;
import org.junit.After;
import org.junit.Before;

/**
 * Created by sleski on 30.06.2015.
 */
public class MainTest {

    public void destroy() {
        AppData.destroySingleton();
    }
}
