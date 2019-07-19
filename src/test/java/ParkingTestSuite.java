import Traffic.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({MainTest.class,
        ExistingEnvironmentMainTest.class,
        ReservationTest.class,
        ReservationAvaliableParametrizedTest.class,
        ParametrizedRezerwationTest.class,
        MainTestParametric.class,
        TimeTest.class
        })
public class ParkingTestSuite {}