package Traffic;

import org.junit.*;
import org.mockito.Mockito;


public class MainTest {

    IparkingFree iparkingFree;
    private static Parking parking;

    @BeforeClass
    public static void init() {}

    @Before
    public void setUp() {
        iparkingFree = Mockito.mock(IparkingFree.class);
        parking = new Parking(iparkingFree);
        parking = new Parking(10);
        parking = Mockito.spy(parking);
    }

    @AfterClass
    public static void releaseeverything() {}

    @After
    public void clear() {
        parking.reset();
    }

    @Test
    public void addPlaceTest() {
        //Given
        //When
        parking.reservPlace(1);
        //Then
        Assert.assertTrue(parking.occupiedPlaces == 1);
    }

    @Test
    public void reservAndRelease() {
        //Given
        //When
        parking.reservPlace(1);
        parking.release(1);
        //Then
        Assert.assertEquals(parking.places[1], false);
    }

    @Test
    public void reservReservedInRage() {
        //Given
        //When
     //   Mockito.when(iparkingFree.inRange(6)).thenReturn(true);
        parking.reservPlace(3);
        //Then
        Assert.assertEquals(parking.reservPlace(3), 2);
    }

    @Test
    public void reservNotReservedInRange() {
        //Given
        //When
        parking.reservPlace(6);
       // Mockito.when(iparkingFree.inRange(6)).thenReturn(true);
        //Then
        Assert.assertEquals(parking.reservPlace(7), 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void reservNotInRangeLow() {
        //Given
        //When
       // Mockito.when(iparkingFree.inRange(6)).thenReturn(false);
        parking.reservPlace(-4);
    }

    @Test
    public void releaseFreeInRange() {
        //Given
        //When
        parking.reservPlace(6);
      //  Mockito.when(parking.inRange(6)).thenReturn(true);
        //Then
        Assert.assertEquals(parking.release(7), 2);
    }

    @Test
    public void releaseOccupiedInRange() {
        //Given
        //When
        parking.reservPlace(6);
     //   Mockito.when(parking.isFree(6)).thenReturn(false);

        //
        //Then
        Assert.assertEquals(parking.release(6), 1);
    }

    @Test
    public void releaseNotInRangeLow() {
        //Given
        //When
        parking.reservPlace(6);
        //Then
        Assert.assertEquals(parking.release(-6), 0);
    }

    @Test
    public void releaseNotInRangeHigh() {
        //Given
        //When
          parking.reservPlace(6);
       // Mockito.when(parking.inRange(6)).thenReturn(false);
        //Then
        Assert.assertEquals(parking.release(42), 0);
    }

    @Test
    public void doubleReserv() {
        parking.reservPlace(1);
        Assert.assertEquals(parking.reservPlace(1), 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void reservNotExistingPlace() {
        Mockito.when(parking.inRange(-123)).thenReturn(false);
        parking.reservPlace(-123);
    }

    @Test
    public void hasReservationChangedStatustrue() {
        //Given
        //When
        parking.reservPlace(1);

        //Then
        Assert.assertEquals(parking.isReserved(1), true);
    }

    @Test
    public void hasReservationChangedStatusfalse() {
        //Given
        //When
        parking.reservPlace(1);
        //Then
        Assert.assertEquals(parking.isReserved(2), false);
    }

    @Test
    public void outOfRangePlaceLow() {
        Assert.assertEquals(parking.inRange(-3), false);
    }

    @Test
    public void outOfRangePlaceHigh() {
        Assert.assertEquals(parking.inRange(20), false);
    }

    @Test
    public void outOfRangePlaceIn() {
        Assert.assertEquals(parking.inRange(4), true);
    }

    @Test
    public void isFreeReserved() {
        //Given
        //When
        parking.reservPlace(1);
        //Then
        Assert.assertEquals(parking.isFree(1), false);
    }

    @Test
    public void isFreeNotReserved() {
        //Given
        //When
        parking.reservPlace(1);
        Mockito.when(iparkingFree.isFree(1)).thenReturn(true);
        //Then
        Assert.assertEquals(parking.isFree(2), true);
    }

    @Test
    public void allfalseAfterReset() {
        for (int i = 0; i < parking.numberOfPlaces; i++) {
            parking.reservPlace(i);
        }

        parking.reset();

        for (int i = 0; i < parking.numberOfPlaces; i++) {
            Assert.assertEquals(parking.isFree(i), true);
        }
    }
}