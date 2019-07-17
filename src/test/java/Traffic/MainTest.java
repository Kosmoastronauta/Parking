package Traffic;

import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mockito;


public class MainTest {

    IparkingFree iparkingFree;


    private static Parking parking;


    @BeforeClass
    public static void init() {

    }

    @Before
    public void setUp() {
        iparkingFree = Mockito.mock(IparkingFree.class);
        parking = new Parking(iparkingFree);
        parking = new Parking(10);
        parking = Mockito.spy(parking);
    }

    @AfterClass
    public static void releaseeverything() {

    }

    @After
    public void clear() {
        parking.reset();
    }

    @Test
    public void addSpaceTest() {
        //Given
        //When
        parking.reservSpace(1);
        //Then
        Assert.assertTrue(parking.occupied_spaces == 1);
    }

    @Test
    public void reservAndRelease() {
        //Given
        //When
        parking.reservSpace(1);
        parking.release(1);
        //Then
        Assert.assertEquals(parking.spaces[1], false);
    }

    @Test
    public void reservReservedInRage() {
        //Given
        //When
     //   Mockito.when(iparkingFree.inRange(6)).thenReturn(true);
        parking.reservSpace(3);
        //Then
        Assert.assertEquals(parking.reservSpace(3), 2);
    }

    @Test
    public void reservNotReservedInRange() {
        //Given
        //When
        parking.reservSpace(6);
       // Mockito.when(iparkingFree.inRange(6)).thenReturn(true);
        //Then
        Assert.assertEquals(parking.reservSpace(7), 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void reservNotInRangeLow() {
        //Given
        //When
       // Mockito.when(iparkingFree.inRange(6)).thenReturn(false);
        parking.reservSpace(-4);
    }

    @Test
    public void releaseFreeInRange() {
        //Given
        //When
        parking.reservSpace(6);
      //  Mockito.when(parking.inRange(6)).thenReturn(true);
        //Then
        Assert.assertEquals(parking.release(7), 2);
    }

    @Test
    public void releaseOccupiedInRange() {
        //Given
        //When
        parking.reservSpace(6);
     //   Mockito.when(parking.isFree(6)).thenReturn(false);

        //
        //Then
        Assert.assertEquals(parking.release(6), 1);
    }

    @Test
    public void releaseNotInRangeLow() {
        //Given
        //When
        parking.reservSpace(6);
        //Then
        Assert.assertEquals(parking.release(-6), 0);
    }

    @Test
    public void releaseNotInRangeHigh() {
        //Given
        //When
          parking.reservSpace(6);
       // Mockito.when(parking.inRange(6)).thenReturn(false);
        //Then
        Assert.assertEquals(parking.release(42), 0);
    }

    @Test
    public void doubleReserv() {
        parking.reservSpace(1);
        Assert.assertEquals(parking.reservSpace(1), 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void reservNotExistingSpace() {
        Mockito.when(parking.inRange(-123)).thenReturn(false);
        parking.reservSpace(-123);
    }

    @Test
    public void hasReservationChangedStatustrue() {
        //Given
        //When
        parking.reservSpace(1);

        //Then
        Assert.assertEquals(parking.isReserved(1), true);
    }

    @Test
    public void hasReservationChangedStatusfalse() {
        //Given
        //When
        parking.reservSpace(1);
        //Then
        Assert.assertEquals(parking.isReserved(2), false);
    }

    @Test
    public void outOfRangeSpaceLow() {
        Assert.assertEquals(parking.inRange(-3), false);
    }

    @Test
    public void outOfRangeSpaceHigh() {
        Assert.assertEquals(parking.inRange(20), false);
    }

    @Test
    public void outOfRangeSpaceIn() {
        Assert.assertEquals(parking.inRange(4), true);
    }

    @Test
    public void isFreeReserved() {
        //Given
        //When
        parking.reservSpace(1);
        //Then
        Assert.assertEquals(parking.isFree(1), false);
    }

    @Test
    public void isFreeNotReserved() {
        //Given
        //When
        parking.reservSpace(1);
        Mockito.when(iparkingFree.isFree(1)).thenReturn(true);
        //Then
        Assert.assertEquals(parking.isFree(2), true);
    }


    @Test
    public void allfalseAfterReset() {
        for (int i = 0; i < parking.number_of_spaces; i++) {
            parking.reservSpace(i);
        }

        parking.reset();

        for (int i = 0; i < parking.number_of_spaces; i++) {
            Assert.assertEquals(parking.isFree(i), true);
        }
    }


}