package Traffic;

import org.junit.*;
import org.mockito.Mockito;


public class MainTest
{

    IparkingFree iparkingFree;
    private static Parking parking;

    @BeforeClass
    public static void init() {}

    @Before
    public void setUp()
    {
        iparkingFree = Mockito.mock(IparkingFree.class);
        parking = new Parking(iparkingFree);
        parking = new Parking(10, "Funny Parking");
        parking = Mockito.spy(parking);
    }

    @AfterClass
    public static void releaseeverything() {}

    @After
    public void clear() {}

    @Test
    public void outOfRangePlaceLow()
    {
        Assert.assertFalse(parking.inRange(-3));
    }

    @Test
    public void outOfRangePlaceHigh()
    {
        Assert.assertFalse(parking.inRange(20));
    }

    @Test
    public void outOfRangePlaceIn()
    {
        Assert.assertTrue(parking.inRange(4));
    }

}