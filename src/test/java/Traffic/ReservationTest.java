package Traffic;

import org.junit.*;

public class ReservationTest
{
    private static Reservation reservation;
    private static Parking parking;
    private static Parking parking2;
    private static Time tempTime;
    private static Time tempTime2;

    @BeforeClass
    public static void init()
    {
        parking = new Parking(10, "TempParking");
        parking2 = new Parking(20, "Sad Parking");
        parking.setId(0);
        parking2.setId(1);
        reservation = new Reservation(parking);
    }

    @Before
    public void setUp()
    {
        tempTime = new Time(15, 30, 17, 15);
        tempTime2 = new Time(16, 40, 18, 50);
        Parking.idGenerator = 0;
        reservation.resetParking(0);
    }

    @AfterClass
    public static void releaseeverything() {}

    @After
    public void clear()
    {
        reservation.resetParking(0);
        // reservation.resetParking(1);
    }

    @Test
    public void addSpaceTest()
    {
        //Given
        //When
        reservation.reservePlace(0, 1, tempTime);
        //Then
        Assert.assertTrue(reservation.occupiedPlaces.get(0) == 1);
    }

    @Test
    public void reserveAndRelease()
    {
        //Given
        //When
        reservation.reservePlace(0, 1, tempTime);
        reservation.release(0, 1, tempTime);
        //Then
        Assert.assertFalse(reservation.places.get(0)[1]);
    }

    @Test
    public void reserveReservedInRage()
    {
        //Given
        //When
        //   Mockito.when(iparkingFree.inRange(6)).thenReturn(true);
        reservation.reservePlace(0, 3, tempTime);
        //Then
        Assert.assertEquals(reservation.reservePlace(0, 3, tempTime2), 2);
    }

    @Test
    public void reserveNotReservedInRange()
    {
        //Given
        //When
        reservation.reservePlace(0, 6, tempTime);
        // Mockito.when(iparkingFree.inRange(6)).thenReturn(true);
        //Then
        Assert.assertEquals(reservation.reservePlace(0, 7, tempTime), 1);
    }

    @Test
    public void reserveNotInRangeLow()
    {
        //Given
        //When
        // Mockito.when(iparkingFree.inRange(6)).thenReturn(false);
        Assert.assertEquals(reservation.reservePlace(0, -4, tempTime), 0);
    }

    @Test
    public void releaseFreeInRange()
    {
        //Given
        //When
        reservation.reservePlace(0, 6, tempTime);
        //Then
        Assert.assertEquals(reservation.release(0, 7, tempTime), 2);
    }

    @Test
    public void releaseOccupiedInRange()
    {
        //Given
        //When
        reservation.reservePlace(0, 6, tempTime);
        //   Mockito.when(parking.isFree(6)).thenReturn(false);
        //Then
        Assert.assertEquals(reservation.release(0, 6, tempTime), 1);
    }

    @Test
    public void releaseNotInRangeLow()
    {
        //Given
        //When
        reservation.reservePlace(0, 6, tempTime);
        //Then
        Assert.assertEquals(reservation.release(0, -6, tempTime), 0);
    }

    @Test
    public void releaseNotInRangeHigh()
    {
        //Given
        //When
        reservation.reservePlace(0, 6, tempTime);
        // Mockito.when(parking.inRange(6)).thenReturn(false);
        //Then
        Assert.assertEquals(reservation.release(0, 42, tempTime), 0);
    }

    @Test
    public void doubleReserve()
    {
        reservation.reservePlace(0, 1, tempTime);
        Assert.assertEquals(reservation.reservePlace(0, 1, tempTime), 2);
    }

    @Test
    public void reserveNotExistingPlace()
    {
        Assert.assertEquals(reservation.reservePlace(0, -123, tempTime), 0);
    }

    @Test
    public void hasReservationChangedStatusFalse()
    {
        //Given
        //When
        reservation.reservePlace(0, 1, tempTime);
        //Then
        Assert.assertFalse(reservation.isPlacePossibleToReserve(tempTime2, tempTime));
    }

    @Test
    public void outOfRangeSpaceLow()
    {
        Assert.assertFalse(parking.inRange(-3));
    }

    @Test
    public void outOfRangeSpaceHigh()
    {
        Assert.assertFalse(parking.inRange(20));
    }

    @Test
    public void outOfRangeSpaceIn()
    {
        Assert.assertTrue(parking.inRange(4));
    }

    @Test
    public void isFreeReserved()
    {
        //Given
        //When
        reservation.reservePlace(0, 1, tempTime);
        //Then
        Assert.assertFalse(reservation.isFreePlace(0, 1));
    }

    @Test
    public void isFreeNotReserved()
    {
        //Given
        //When
        reservation.reservePlace(0, 1, tempTime);
        //Then
        Assert.assertFalse(reservation.isFreePlace(0, 1));
    }

    @Test
    public void allFalseAfterReset()
    {
        for(int i = 0; i < reservation.parkings.get(0).numberOfPlaces; i++)
        {
            reservation.reservePlace(0, i, tempTime);
        }

        reservation.resetParking(0);

        for(int i = 0; i < reservation.parkings.get(0).numberOfPlaces; i++)
        {
            Assert.assertTrue(reservation.isFreePlace(0, i));
        }
    }

    @Test
    public void timeConflict()
    {
        Assert.assertFalse(reservation.isPlacePossibleToReserve(tempTime, tempTime2));
    }

    @Test
    public void wereReservationWithNoReservationBefore()
    {
        for(int i = 0; i < reservation.parkings.get(0).numberOfPlaces; i++)
        {
            Assert.assertFalse(reservation.isReservation(0, i));
        }
    }

    @Test
    public void wereReservationWWithReservationsBefore()
    {
        for(int i = 0; i < reservation.parkings.get(0).numberOfPlaces; i++)
        {
            reservation.reservePlace(0, i, new Time(15, 40, 18, 15));
            Assert.assertTrue(reservation.isReservation(0, i));
        }
    }

    @Test
    public void wereReservationWWithNoReservationsBeforeButNotEmptyParking()
    {
        for(int i = 0; i < reservation.parkings.get(0).numberOfPlaces - 1; i++)
        {
            reservation.reservePlace(0, i, new Time(17, 15, 18, 40));
            Assert.assertFalse(reservation.isReservation(0, i + 1));
        }
    }

    @Test
    public void avaliableNotEmptyReservedInPossibleToReserveTime()
    {
        reservation.reservePlace(0, 5, new Time(15, 30, 17, 16));
        Assert.assertTrue(reservation.isAvaliablePlaceInTime(0, 5, new Time(17, 40, 18, 40)));
    }

    @Test
    public void avaliableNotEmptyReservedInImpossibleReserveTime()
    {
        reservation.reservePlace(0, 5, new Time(15, 30, 17, 16));
        Assert.assertFalse(reservation.isAvaliablePlaceInTime(0, 5, new Time(17, 15, 18, 40)));
    }

    @Test
    public void isListOfTimesEmptyAfterReset()
    {
        //Given
        //When
        reservation.reservePlace(0, 0, tempTime);
        reservation.resetParking(0);
        Assert.assertTrue(reservation.times.get(0).isEmpty());
    }


    @Test
    public void avaliableReservationSamePlaceSameTime()
    {
        //Given Empty Parking
        //When
        reservation.reservePlace(0, 1, new Time(1, 10, 12, 15));
        //Then
        Assert.assertFalse(reservation.isAvaliablePlaceInTime(0, 1, new Time(1, 10, 12, 15)));
    }

    @Test
    public void differentParkingSameSpaceSameTimeReservation()
    {
        //Given
        reservation = new Reservation(parking);
        reservation.reservePlace(0, 1, tempTime);
        reservation.addParking(parking2);
        Assert.assertEquals(reservation.reservePlace(parking2.getId(), 1, tempTime), 1);
    }

    @Test
    public void reservationOnEmptyParking()
    {
        Assert.assertEquals(reservation.reservePlace(0, 0, tempTime), 1);
    }

    @Test
    public void addParkingNoBefore()
    {
        reservation = new Reservation(parking);
        parking2.setId(1);
        Assert.assertEquals(reservation.addParking(parking2), 1);
    }

    @Test
    public void addParkingWasBefore()
    {
        reservation = new Reservation(parking);
        Assert.assertEquals(reservation.addParking(parking), 0);
    }

    @Test
    public void isAvaliablePlaceOnAnotherParkingSamePlaceSameTimeFirstReserved()
    {
        //Given
        //When
        reservation = new Reservation(parking);
        reservation.reservePlace(parking.getId(), 0, tempTime);
        reservation.addParking(parking2);
        //Then
        Assert.assertTrue(reservation.isAvaliablePlaceInTime(parking2.getId(), 0, tempTime));
    }

    @Test
    public void isParkingInReservationNot()
    {
        reservation = new Reservation(parking);
        Assert.assertFalse(reservation.isParkingInReseservation(parking2.getId()));
    }

    @Test
    public void isParkingInReservationYes()
    {
        Assert.assertTrue(reservation.isParkingInReseservation(parking.getId()));
    }

    @Test
    public void releaseInOneParkingSecondNoChanges()
    {
        //Given
        reservation = new Reservation(parking);
        reservation.reservePlace(parking.getId(), 0, tempTime);
        reservation.addParking(parking2);
        Assert.assertEquals(reservation.release(parking2.getId(), 0, tempTime), 2);
        //When

    }
}