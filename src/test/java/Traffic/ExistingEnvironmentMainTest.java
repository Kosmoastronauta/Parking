package Traffic;

import org.junit.*;
import org.mockito.Mockito;

public class ExistingEnvironmentMainTest {


    private static Parking parking1;
    private static Parking parking2;

    @BeforeClass
    public static void init()
    {

    }

    @Before
    public void setUp()
    {
        parking1 = new Parking(10);
        parking2 = new Parking(20);
    }

    @AfterClass
    public static void releaseeverything()
    {

    }

    @After
    public void clear()
    {
        parking1.reset();
        parking2.reset();
    }


    @Test
    public  void addSpaceTest()
    {
        //Given
        //When
        parking1.reservSpace(1);
        parking2.reservSpace(1);
        //Then
        Assert.assertTrue(parking1.occupied_spaces == 1);
        Assert.assertTrue(parking2.occupied_spaces == 1);
    }

    @Test
    public void reservAndRelease()
    {
        //Given
        //When
        parking1.reservSpace(1);
        parking1.release(1);
        parking2.reservSpace(15);
        parking2.release(15);
        //Then
        Assert.assertEquals(parking1.spaces[1],false);
        Assert.assertEquals(parking2.spaces[1],false);
    }

    @Test
    public void reservReservedInRage()
    {
        //Given
        //When
        parking1.reservSpace(3);
        parking2.reservSpace(1);
        //Then
        Assert.assertEquals(parking1.reservSpace(3),2);
        Assert.assertEquals(parking2.reservSpace(1),2);
    }

    @Test
    public void reservNotReservedInRange()
    {
        //Given
        //When
        parking1.reservSpace(6);
        parking2.reservSpace(14);
        //Then
        Assert.assertEquals(parking1.reservSpace(7),1);
        Assert.assertEquals(parking2.reservSpace(2),1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void reservNotInRangeLow()
    {
        //Given
        //When
        parking1.reservSpace(-4);
        parking1.reservSpace(-15);
    }

    @Test
    public void releaseFreeInRange()
    {
        //Given
        //When
        parking1.reservSpace(6);
        parking2.reservSpace(4);
        //Then
        Assert.assertEquals(parking1.release(7),2);
        Assert.assertEquals(parking1.release(7),2);
    }

    @Test
    public void releaseOccupiedInRange()
    {
        //Given
        //When
        parking1.reservSpace(6);
        parking2.reservSpace(15);
        //Then
        Assert.assertEquals(parking1.release(6),1);
        Assert.assertEquals(parking2.release(15),1);
    }

    @Test
    public void releaseNotInRangeLow()
    {
        //Given
        //When
        parking1.reservSpace(6);
        parking2.reservSpace(12);
        //Then
        Assert.assertEquals(parking1.release(-6),0);
        Assert.assertEquals(parking2.release(-50),0);
    }

    @Test
    public void releaseNotInRangeHigh()
    {
        //Given
        //When
        parking1.reservSpace(6);
        parking2.reservSpace(19);
        //Then
        Assert.assertEquals(parking2.release(42),0);
        Assert.assertEquals(parking2.release(100),0);
    }

    @Test
    public void doubleReserv()
    {
        parking1.reservSpace(1);
        parking2.reservSpace(14);
        Assert.assertEquals(parking1.reservSpace(1),2);
        Assert.assertEquals(parking2.reservSpace(14),2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void reservNotExistingSpace()
    {
        parking1.reservSpace(-123);
        parking2.reservSpace(-13);
    }

    @Test
    public void hasReservationChangedStatusTrue()
    {
        //Given
        //When
        parking1.reservSpace(1);
        parking2.reservSpace(13);
        //Then
        Assert.assertEquals(parking1.isReserved(1), true);
        Assert.assertEquals(parking2.isReserved(13), true);
    }

    @Test
    public void hasReservationChangedStatusFalse()
    {
        //Given
        //When
        parking1.reservSpace(9);
        parking2.reservSpace(3);
        //Then
        Assert.assertEquals(parking1.isReserved(3), false);
    }

    @Test
    public void outOfRangeSpaceLow()
    {
        Assert.assertEquals(parking1.inRange(-3), false);
        Assert.assertEquals(parking2.inRange(-5), false);
    }

    @Test
    public void outOfRangeSpaceHigh()
    {
        Assert.assertEquals(parking1.inRange(20), false);
        Assert.assertEquals(parking2.inRange(30), false);
    }

    @Test
    public void outOfRangeSpaceIn()
    {
        Assert.assertEquals(parking1.inRange(4),true);
        Assert.assertEquals(parking2.inRange(13),true);
    }

    @Test
    public void isFreeReserved()
    {
        //Given
        //When
        parking1.reservSpace(1);
        parking2.reservSpace(5);
        //Then
        Assert.assertEquals(parking1.isFree(1),false);
        Assert.assertEquals(parking2.isFree(5),false);
    }

    @Test
    public void isFreeNotReserved()
    {
        //Given
        //When
        parking1.reservSpace(1);
        parking2.reservSpace(5);
        //Then
        Assert.assertEquals(parking1.isFree(5),true);
    }

    @Test
    public void allFalseAfterReset()
    {
        for(int i=0; i<parking1.number_of_spaces; i++)
        {
            parking1.reservSpace(i);
        }
        for(int i=0; i<parking2.number_of_spaces; i++)
        {
            parking2.reservSpace(i);
        }

        parking1.reset();
        parking2.reset();

        for(int i=0; i<parking1.number_of_spaces; i++)
        {
            Assert.assertEquals(parking1.isFree(i),true);
        }

        for(int i=0; i<parking2.number_of_spaces; i++)
        {
            Assert.assertEquals(parking2.isFree(i),true);
        }
    }
}
