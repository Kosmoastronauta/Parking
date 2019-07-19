package Traffic;

import org.junit.*;

public class ReservationTest {

    private static Reservation reservation;
    private static Parking parking;
    private static Time tempTime;
    private static Time tempTime2;

    @BeforeClass
    public static void init() {}


    @Before
    public void setUp() {
        parking = new Parking(10,"TempParking");
        Parking.idGenerator = 0;
        reservation = new Reservation(parking);
        tempTime = new Time(15,30,17,15);
        tempTime2 = new Time (16,40,18,50);
       // reservation.resetParking(0);
    }



    @AfterClass
    public static void releaseeverything() {}

    @After
    public void clear() {
        reservation.resetParking(0);
    }

    @Test
    public void addSpaceTest() {
        //Given
        //When
        reservation.reservePlace(0,1,tempTime);
        //Then
        Assert.assertTrue(reservation.occupiedPlaces.get(0) == 1);
    }

    @Test
    public void reservAndRelease() {
        //Given
        //When
        reservation.reservePlace(0,1,tempTime);
        reservation.release(0,1, tempTime);
        //Then
        Assert.assertEquals(reservation.places.get(0)[1], false);
    }

    @Test
    public void reservReservedInRage() {
        //Given
        //When
        //   Mockito.when(iparkingFree.inRange(6)).thenReturn(true);
        reservation.reservePlace(0,3,tempTime);
        //Then
        Assert.assertEquals(reservation.reservePlace(0,3,tempTime2), 2);
    }

    @Test
    public void reservNotReservedInRange() {
        //Given
        //When
        reservation.reservePlace(0,6,tempTime);
        // Mockito.when(iparkingFree.inRange(6)).thenReturn(true);
        //Then
        Assert.assertEquals(reservation.reservePlace(0,7,tempTime), 1);
    }

    @Test
    public void reservNotInRangeLow() {
        //Given
        //When
        // Mockito.when(iparkingFree.inRange(6)).thenReturn(false);
        Assert.assertEquals(reservation.reservePlace(0,-4,tempTime), 0);
    }

    @Test
    public void releaseFreeInRange() {
        //Given
        //When
        reservation.reservePlace(0,6,tempTime);
        //Then
        Assert.assertEquals(reservation.release(0,7, tempTime), 2);
    }

    @Test
    public void releaseOccupiedInRange()
    {
        //Given
        //When
        reservation.reservePlace(0,6,tempTime);
        //   Mockito.when(parking.isFree(6)).thenReturn(false);
        //Then
        Assert.assertEquals(reservation.release(0,6, tempTime), 1);
    }

    @Test
    public void releaseNotInRangeLow()
    {
        //Given
        //When
        reservation.reservePlace(0,6,tempTime);
        //Then
        Assert.assertEquals(reservation.release(0,-6,tempTime), 0);
    }

    @Test
    public void releaseNotInRangeHigh()
    {
        //Given
        //When
        reservation.reservePlace(0,6, tempTime);
        // Mockito.when(parking.inRange(6)).thenReturn(false);
        //Then
        Assert.assertEquals(reservation.release(0,42, tempTime), 0);
    }

    @Test
    public void doubleReserv() {
        reservation.reservePlace(0,1, tempTime);
        Assert.assertEquals(reservation.reservePlace(0,1, tempTime), 2);
    }

    @Test
    public void reservNotExistingPlace()
    {
        Assert.assertEquals(reservation.reservePlace(0,-123, tempTime),0);
    }

    @Test
    public void hasReservationChangedStatusfalse()
    {
        //Given
        //When
        reservation.reservePlace(0,1, tempTime);
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
        reservation.reservePlace(0,1, tempTime);
        //Then
        Assert.assertEquals(reservation.isFreePlace(0,1), false);
    }

    @Test
    public void isFreeNotReserved() {
        //Given
        //When
        reservation.reservePlace(0,1, tempTime);
        //Then
        Assert.assertEquals(reservation.isFreePlace(0,1), false);
    }


    @Test
    public void allfalseAfterReset() {
        for (int i = 0; i < reservation.parkings.get(0).numberOfPlaces; i++) {
            reservation.reservePlace(0,i, tempTime);
        }

        reservation.resetParking(0);

        for (int i = 0; i < reservation.parkings.get(0).numberOfPlaces; i++) {
            Assert.assertEquals(reservation.isFreePlace(0, i), true);
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
        for(int i = 0; i<reservation.parkings.get(0).numberOfPlaces; i++)
        {
            Assert.assertEquals(reservation.isReservation(0, i),false);
        }
    }

    @Test
    public void wereReservationWWithReservationsBefore()
    {
        for(int i = 0; i <reservation.parkings.get(0).numberOfPlaces; i++ )
        {
            reservation.reservePlace(0, i,new Time(15,40,18,15));
            Assert.assertEquals(reservation.isReservation(0, i),true);
        }
    }

    @Test
    public void wereReservationWWithNoReservationsBeforeButNotEmptyParking()
    {
        for(int i = 0; i <reservation.parkings.get(0).numberOfPlaces -1; i++ )
        {
            reservation.reservePlace(0, i, new Time(17,15,18,40));
            Assert.assertEquals(reservation.isReservation(0,i+1),false);
        }
    }

    @Test
    public void avaliableNotEmptyReservedInPossibletoReserveTime()
    {
        reservation.reservePlace(0,5,new Time(15,30,17,16));

        Assert.assertEquals(reservation.isAvaliable(0,5, new Time (17,40,18,40)),true);
    }

    @Test
    public void avaliableNotEmptyReservedInImpossibleReserveTime()
    {
        reservation.reservePlace(0,5,new Time(15,30,17,16));

        Assert.assertEquals(reservation.isAvaliable(0,5, new Time (17,15,18,40)), false);
    }

    @Test
    public void reservationOnEmptyParking()
    {
        Assert.assertEquals(reservation.reservePlace(0,0,tempTime),1);
    }

}