package Traffic;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class MainTestParametric
{

    private static Parking parking;
    private int currentPlace;
    private boolean currentRangeStatus;

    private MainTestParametric() {}

    public MainTestParametric(int currentPlace, boolean currentRangeStatus)
    {
        this.currentPlace = currentPlace;
        this.currentRangeStatus = currentRangeStatus;
    }

    @BeforeClass
    public static void setUp()
    {
        parking = new Parking(10);
    }

    @AfterClass
    public static void releaseeverything()
    {
        parking.reset();
    }

    @After
    public void clear()
    {
        parking.reset();
    }

    @Test
    public  void addPlaceTest()
    {
        //Given
        //When
        parking.reservePlace(1);
        //Then
        Assert.assertTrue(parking.occupiedPlaces == 1);
    }

    @Test
    public void reservAndRelease()
    {
        //Given
        //When
        parking.reservePlace(1);
        parking.release(1);
        //Then
        Assert.assertEquals(parking.places[1],false);
    }

    @Test
    public void reservReservedInRage()
    {
        //Given
        //When
       // Mockito.when(iparkingFree.inRange(6)).thenReturn(true);
        parking.reservePlace(3);
        //Then
        Assert.assertEquals(parking.reservePlace(3),2);
    }

    @Test
    public void reservNotReservedInRange()
    {
        //Given
        //When
        parking.reservePlace(6);
      //  Mockito.when(iparkingFree.inRange(6)).thenReturn(true);
        //Then
        Assert.assertEquals(parking.reservePlace(7),1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void reservNotInRangeLow()
    {
        //Given
        //When
       // Mockito.when(iparkingFree.inRange(6)).thenReturn(false);
        parking.reservePlace(-4);
    }

    @Test
    public void releaseFreeInRange()
    {
        //Given
        //When
        parking.reservePlace(6);
//        Mockito.when(parking.inRange(6)).thenReturn(true);
        //Then
        Assert.assertEquals(parking.release(7),2);
    }

    @Test
    public void releaseOccupiedInRange()
    {
        //Given
        //When
        parking.reservePlace(6);
       // Mockito.when(parking.isFree(6)).thenReturn(false);

        //
        //Then
        Assert.assertEquals(parking.release(6),1);
    }

    @Test
    public void releaseNotInRangeLow()
    {
        //Given
        //When
        parking.reservePlace(6);
        //Then
        Assert.assertEquals(parking.release(-6),0);
    }

    @Test
    public void releaseNotInRangeHigh()
    {
        //Given
        //When
        parking.reservePlace(6);
//        Mockito.when(parking.inRange(6)).thenReturn(false);
        //Then
        Assert.assertEquals(parking.release(42),0);
    }

    @Test
    public void doubleReserv()
    {
        parking.reservePlace(1);
        Assert.assertEquals(parking.reservePlace(1),2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void reservNotExistingPlace()
    {
        //Mockito.when(parking.inRange(-123)).thenReturn(false);
        parking.reservePlace(-123);
    }

    @Test
    public void hasReservationChangedStatusTrue()
    {
        //Given
        //When
        parking.reservePlace(1);

        //Then
        Assert.assertEquals(parking.isReserved(1), true);
    }

    @Test
    public void hasReservationChangedStatusFalse()
    {
        //Given
        //When
        parking.reservePlace(1);
        //Then
        Assert.assertEquals(parking.isReserved(2), false);
    }

    @Test
    public void outOfRangePlaceLow()
    {
        Assert.assertEquals(parking.inRange(-3), false);
    }

    @Test
    public void outOfRangePlaceHigh()
    {
        Assert.assertEquals(parking.inRange(20), false);
    }

    @Test
    public void outOfRangePlaceIn()
    {
        Assert.assertEquals(parking.inRange(4),true);
    }

    @Test
    public void isFreeReserved()
    {
        //Given
        //When
        parking.reservePlace(1);
        //Then
        Assert.assertEquals(parking.isFree(1),false);
    }

    @Test
    public void isFreeNotReserved()
    {
        //Given
        //When
        parking.reservePlace(1);
      //  Mockito.when(iparkingFree.isFree(1)).thenReturn(true);
        //Then
        Assert.assertEquals(parking.isFree(2),true);
    }

    @Test
    public void allFalseAfterReset()
    {
        for(int i = 0; i<parking.numberOfPlaces; i++)
        {
            parking.reservePlace(i);
        }

        parking.reset();

        for(int i = 0; i<parking.numberOfPlaces; i++)
        {
            Assert.assertEquals(parking.isFree(i),true);
        }
    }

    @Test
    public void inRangeSeries()
    {
        Assert.assertEquals(parking.inRange(currentPlace),currentRangeStatus);
    }

    @Parameterized.Parameters
    public static Collection inputData() {
        return Arrays.asList(new Object[][]

                {{-1, false},
                        {0, true},
                        {1, true},
                        {2, true},
                        {3, true},
                        {4, true},
                        {5, true},
                        {6, true},
                        {7, true},
                        {8, true},
                        {9, true},
                        {10, false},
                        {11, false},
                        {12, false},
                        {13, false},
                        {14, false},
                        {15, false},
                        {16, false}});
    }
}