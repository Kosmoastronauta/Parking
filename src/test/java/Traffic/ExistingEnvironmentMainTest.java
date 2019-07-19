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
        parking1 = new Parking(10);
        parking2 = new Parking(20);
    }

    @AfterClass
    public static void releaseeverything() {}

    @After
    public void clear() {}

    @Test
    public void outOfRangePlaceLow()
    {
        Assert.assertEquals(parking1.inRange(-3), false);
        Assert.assertEquals(parking2.inRange(-5), false);
    }

    @Test
    public void outOfRangePlaceHigh()
    {
        Assert.assertEquals(parking1.inRange(20), false);
        Assert.assertEquals(parking2.inRange(30), false);
    }

    @Test
    public void outOfRangePlaceIn()
    {
        Assert.assertEquals(parking1.inRange(4),true);
        Assert.assertEquals(parking2.inRange(13),true);
    }
}