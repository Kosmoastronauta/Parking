package Traffic;

import org.junit.*;

public class ExistingEnvironmentMainTest {

    private static Parking parking1;
    private static Parking parking2;

    @BeforeClass
    public static void init() {}

    @Before
    public void setUp()
    {
        parking1 = new Parking(10, "Funny Parking");
        parking2 = new Parking(20, "Sad Parking");
    }

    @AfterClass
    public static void releaseeverything() {}

    @After
    public void clear() {}

    @Test
    public void outOfRangePlaceLow()
    {
        Assert.assertFalse(parking1.inRange(-3));
        Assert.assertFalse(parking2.inRange(-5));
    }

    @Test
    public void outOfRangePlaceHigh()
    {
        Assert.assertFalse(parking1.inRange(20));
        Assert.assertFalse(parking2.inRange(30));
    }

    @Test
    public void outOfRangePlaceIn()
    {
        Assert.assertTrue(parking1.inRange(4));
        Assert.assertTrue(parking2.inRange(13));
    }
}