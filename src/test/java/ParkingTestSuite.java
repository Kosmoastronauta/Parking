import Traffic.ExistingEnvironmentMainTest;
import Traffic.MainTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({MainTest.class, ExistingEnvironmentMainTest.class})
public class ParkingTestSuite {
}
