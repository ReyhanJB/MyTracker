import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ServiceTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeoutException;

import edu.uci.seal.testapp.service.LocationService;

/**
 * Created by reyhanjb on 5/8/17.
 */

@RunWith(AndroidJUnit4.class)
public class testService {

    @Rule
    public final ServiceTestRule mServiceRule = new ServiceTestRule();

    @Test
    public void testLocationService() throws TimeoutException {

        // Start the service and then do anything that requires the service runs in the background
        mServiceRule.startService(new Intent(InstrumentationRegistry.getTargetContext(), LocationService.class));

    }
}
