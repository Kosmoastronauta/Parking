package Traffic;

import org.junit.*;

public class ReservationTest {

    private static Reservation reservation;
    private static Parking parking;
    private static Time tempTime;
    private static Time tempTime2;

    @BeforeClass
    public static void init() {

    }

    @Before
    public void setUp() {
        parking = new Parking(10);
       reservation = new Reservation(parking);
       tempTime = new Time(15,30,17,15);
       tempTime2 = new Time (16,40,18,50);
    }

    @AfterClass
    public static void releaseeverything() {

    }

    @After
    public void clear() {
        reservation.resetParking();
    }

    @Test
    public void addSpaceTest() {
        //Given
        //When
        reservation.reservePlace(1,tempTime);
        //Then
        Assert.assertTrue(reservation.occupiedPlaces == 1);
    }

    @Test
    public void reservAndRelease() {
        //Given
        //When
        reservation.reservePlace(1,tempTime);
        reservation.release(1, tempTime);
        //Then
        Assert.assertEquals(reservation.places[1], false);
    }

    @Test
    public void reservReservedInRage() {
        //Given
        //When
        //   Mockito.when(iparkingFree.inRange(6)).thenReturn(true);
        reservation.reservePlace(3,tempTime);
        //Then
        Assert.assertEquals(reservation.reservePlace(3,tempTime2), 2);
    }

    @Test
    public void reservNotReservedInRange() {
        //Given
        //When
        reservation.reservePlace(6,tempTime);
        // Mockito.when(iparkingFree.inRange(6)).thenReturn(true);
        //Then
        Assert.assertEquals(reservation.reservePlace(7,tempTime), 1);
    }

    @Test
    public void reservNotInRangeLow() {
        //Given
        //When
        // Mockito.when(iparkingFree.inRange(6)).thenReturn(false);
        Assert.assertEquals(reservation.reservePlace(-4,tempTime), 0);
    }

    @Test
    public void releaseFreeInRange() {
        //Given
        //When
        reservation.reservePlace(6,tempTime);
        //Then
        Assert.assertEquals(reservation.release(7, tempTime), 2);
    }

    @Test
    public void releaseOccupiedInRange()
    {
        //Given
        //When
        reservation.reservePlace(6,tempTime);
        //   Mockito.when(parking.isFree(6)).thenReturn(false);
        //Then
        Assert.assertEquals(reservation.release(6, tempTime), 1);
    }

    @Test
    public void releaseNotInRangeLow()
    {
        //Given
        //When
        reservation.reservePlace(6,tempTime);
        //Then
        Assert.assertEquals(reservation.release(-6,tempTime), 0);
    }

    @Test
    public void releaseNotInRangeHigh()
    {
        //Given
        //When
        reservation.reservePlace(6, tempTime);
        // Mockito.when(parking.inRange(6)).thenReturn(false);
        //Then
        Assert.assertEquals(reservation.release(42, tempTime), 0);
    }

    @Test
    public void doubleReserv() {
        reservation.reservePlace(1, tempTime);
        Assert.assertEquals(reservation.reservePlace(1, tempTime), 2);
    }

    @Test
    public void reservNotExistingPlace()
    {
        Assert.assertEquals(reservation.reservePlace(-123, tempTime),0);
    }

    @Test
    public void hasReservationChangedStatusfalse()
    {
        //Given
        //When
        reservation.reservePlace(1, tempTime);
        //Then
        Assert.assertEquals(reservation.isPlacePossibleToReserve(tempTime2, tempTime), false);
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
        reservation.reservePlace(1, tempTime);
        //Then
        Assert.assertEquals(reservation.isFreePlace(1), false);
    }

    @Test
    public void isFreeNotReserved() {
        //Given
        //When
        reservation.reservePlace(1, tempTime);
        //Then
        Assert.assertEquals(reservation.isFreePlace(1), false);
    }


    @Test
    public void allfalseAfterReset() {
        for (int i = 0; i < reservation.parking.numberOfPlaces; i++) {
            reservation.reservePlace(i, tempTime);
        }

        reservation.resetParking();

        for (int i = 0; i < reservation.parking.numberOfPlaces; i++) {
            Assert.assertEquals(reservation.isFreePlace(i), true);
        }
    }

    @Test
    public void timeConflict()
    {
        Assert.assertEquals(reservation.isPlacePossibleToReserve(tempTime,tempTime2), false);
    }

    @Test
    public void wereReservationWithNoReservationBefore()
    {
        for(int i = 0; i<reservation.parking.numberOfPlaces; i++)
        {
            Assert.assertEquals(reservation.isReservation(i),false);
        }
    }

    @Test
    public void wereReservationWWithReservationsBefore()
    {
        for(int i = 0; i <reservation.parking.numberOfPlaces; i++ )
        {
            reservation.reservePlace(i,new Time(15,40,18,15));
            Assert.assertEquals(reservation.isReservation(i),true);
        }
    }

    @Test
    public void wereReservationWWithNoReservationsBeforeButNotEmptyParking()
    {
        for(int i = 0; i <reservation.parking.numberOfPlaces -1; i++ )
        {
            reservation.reservePlace(i,new Time(15,40,18,15));
            Assert.assertEquals(reservation.isReservation(i+1),false);
        }
    }

    @Test
    public void AvaliableNotEmptyReservedInPossibletoReserveTime()
    {
        reservation.reservePlace(5,new Time(15,30,17,16));

        Assert.assertEquals(reservation.isAvaliable(5, new Time (17,40,18,40)),true);
    }

    @Test
    public void AvaliableNotEmptyReservedInImpossibleReserveTime()
    {
        reservation.reservePlace(5,new Time(15,30,17,16));

        Assert.assertEquals(reservation.isAvaliable(5, new Time (17,15,18,40)), false);
    }
}