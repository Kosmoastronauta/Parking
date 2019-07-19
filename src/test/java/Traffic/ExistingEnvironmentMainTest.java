package Traffic;

import org.junit.*;
import org.mockito.Mockito;

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
    public void clear()
    {
        parking1.reset();
        parking2.reset();
    }

    @Test
    public  void addPlaceTest()
    {
        //Given
        //When
        parking1.reservPlace(1);
        parking2.reservPlace(1);
        //Then
        Assert.assertTrue(parking1.occupied_places == 1);
        Assert.assertTrue(parking2.occupied_places == 1);
    }

    @Test
    public void reservAndRelease()
    {
        //Given
        //When
        parking1.reservPlace(1);
        parking1.release(1);
        parking2.reservPlace(15);
        parking2.release(15);
        //Then
        Assert.assertEquals(parking1.places[1],false);
        Assert.assertEquals(parking2.places[1],false);
    }

    @Test
    public void reservReservedInRage()
    {
        //Given
        //When
        parking1.reservPlace(3);
        parking2.reservPlace(1);
        //Then
        Assert.assertEquals(parking1.reservPlace(3),2);
        Assert.assertEquals(parking2.reservPlace(1),2);
    }

    @Test
    public void reservNotReservedInRange()
    {
        //Given
        //When
        parking1.reservPlace(6);
        parking2.reservPlace(14);
        //Then
        Assert.assertEquals(parking1.reservPlace(7),1);
        Assert.assertEquals(parking2.reservPlace(2),1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void reservNotInRangeLow()
    {
        //Given
        //When
        parking1.reservPlace(-4);
        parking1.reservPlace(-15);
    }

    @Test
    public void releaseFreeInRange()
    {
        //Given
        //When
        parking1.reservPlace(6);
        parking2.reservPlace(4);
        //Then
        Assert.assertEquals(parking1.release(7),2);
        Assert.assertEquals(parking1.release(7),2);
    }

    @Test
    public void releaseOccupiedInRange()
    {
        //Given
        //When
        parking1.reservPlace(6);
        parking2.reservPlace(15);
        //Then
        Assert.assertEquals(parking1.release(6),1);
        Assert.assertEquals(parking2.release(15),1);
    }

    @Test
    public void releaseNotInRangeLow()
    {
        //Given
        //When
        parking1.reservPlace(6);
        parking2.reservPlace(12);
        //Then
        Assert.assertEquals(parking1.release(-6),0);
        Assert.assertEquals(parking2.release(-50),0);
    }

    @Test
    public void releaseNotInRangeHigh()
    {
        //Given
        //When
        parking1.reservPlace(6);
        parking2.reservPlace(19);
        //Then
        Assert.assertEquals(parking2.release(42),0);
        Assert.assertEquals(parking2.release(100),0);
    }

    @Test
    public void doubleReserv()
    {
        parking1.reservPlace(1);
        parking2.reservPlace(14);
        Assert.assertEquals(parking1.reservPlace(1),2);
        Assert.assertEquals(parking2.reservPlace(14),2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void reservNotExistingPlace()
    {
        parking1.reservPlace(-123);
        parking2.reservPlace(-13);
    }

    @Test
    public void hasReservationChangedStatusTrue()
    {
        //Given
        //When
        parking1.reservPlace(1);
        parking2.reservPlace(13);
        //Then
        Assert.assertEquals(parking1.isReserved(1), true);
        Assert.assertEquals(parking2.isReserved(13), true);
    }

    @Test
    public void hasReservationChangedStatusFalse()
    {
        //Given
        //When
        parking1.reservPlace(9);
        parking2.reservPlace(3);
        //Then
        Assert.assertEquals(parking1.isReserved(3), false);
    }

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

    @Test
    public void isFreeReserved()
    {
        //Given
        //When
        parking1.reservPlace(1);
        parking2.reservPlace(5);
        //Then
        Assert.assertEquals(parking1.isFree(1),false);
        Assert.assertEquals(parking2.isFree(5),false);
    }

    @Test
    public void isFreeNotReserved()
    {
        //Given
        //When
        parking1.reservPlace(1);
        parking2.reservPlace(5);
        //Then
        Assert.assertEquals(parking1.isFree(5),true);
    }

    @Test
    public void allFalseAfterReset()
    {
        for(int i=0; i<parking1.number_of_places; i++)
        {
            parking1.reservPlace(i);
        }
        for(int i=0; i<parking2.number_of_places; i++)
        {
            parking2.reservPlace(i);
        }

        parking1.reset();
        parking2.reset();

        for(int i=0; i<parking1.number_of_places; i++)
        {
            Assert.assertEquals(parking1.isFree(i),true);
        }

        for(int i=0; i<parking2.number_of_places; i++)
        {
            Assert.assertEquals(parking2.isFree(i),true);
        }
    }
}