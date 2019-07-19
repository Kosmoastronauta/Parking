package Traffic;

import org.junit.*;
import org.mockito.Mockito;

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
        reservation.reservPlace(1,tempTime);
        //Then
        Assert.assertTrue(reservation.occupied_places == 1);
    }

    @Test
    public void reservAndRelease() {
        //Given
        //When
        reservation.reservPlace(1,tempTime);
        reservation.release(1, tempTime);
        //Then
        Assert.assertEquals(reservation.places[1], false);
    }

    @Test
    public void reservReservedInRage() {
        //Given
        //When
        //   Mockito.when(iparkingFree.inRange(6)).thenReturn(true);
        reservation.reservPlace(3,tempTime);
        //Then
        Assert.assertEquals(reservation.reservPlace(3,tempTime2), 2);
    }

    @Test
    public void reservNotReservedInRange() {
        //Given
        //When
        reservation.reservPlace(6,tempTime);
        // Mockito.when(iparkingFree.inRange(6)).thenReturn(true);
        //Then
        Assert.assertEquals(reservation.reservPlace(7,tempTime), 1);
    }

    @Test
    public void reservNotInRangeLow() {
        //Given
        //When
        // Mockito.when(iparkingFree.inRange(6)).thenReturn(false);
        Assert.assertEquals(reservation.reservPlace(-4,tempTime), 0);
    }

    @Test
    public void releaseFreeInRange() {
        //Given
        //When
        reservation.reservPlace(6,tempTime);
        //Then
        Assert.assertEquals(reservation.release(7, tempTime), 2);
    }

    @Test
    public void releaseOccupiedInRange()
    {
        //Given
        //When
        reservation.reservPlace(6,tempTime);
        //   Mockito.when(parking.isFree(6)).thenReturn(false);
        //Then
        Assert.assertEquals(reservation.release(6, tempTime), 1);
    }

    @Test
    public void releaseNotInRangeLow()
    {
        //Given
        //When
        reservation.reservPlace(6,tempTime);
        //Then
        Assert.assertEquals(reservation.release(-6,tempTime), 0);
    }

    @Test
    public void releaseNotInRangeHigh()
    {
        //Given
        //When
        reservation.reservPlace(6, tempTime);
        // Mockito.when(parking.inRange(6)).thenReturn(false);
        //Then
        Assert.assertEquals(reservation.release(42, tempTime), 0);
    }

    @Test
    public void doubleReserv() {
        reservation.reservPlace(1, tempTime);
        Assert.assertEquals(reservation.reservPlace(1, tempTime), 2);
    }

    @Test
    public void reservNotExistingPlace()
    {
        Assert.assertEquals(reservation.reservPlace(-123, tempTime),0);
    }

    @Test
    public void hasReservationChangedStatusfalse()
    {
        //Given
        //When
        reservation.reservPlace(1, tempTime);
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
        reservation.reservPlace(1, tempTime);
        //Then
        Assert.assertEquals(reservation.isFreePlace(1), false);
    }

    @Test
    public void isFreeNotReserved() {
        //Given
        //When
        reservation.reservPlace(1, tempTime);
        //Then
        Assert.assertEquals(reservation.isFreePlace(1), false);
    }


    @Test
    public void allfalseAfterReset() {
        for (int i = 0; i < reservation.parking.number_of_places; i++) {
            reservation.reservPlace(i, tempTime);
        }

        reservation.resetParking();

        for (int i = 0; i < reservation.parking.number_of_places; i++) {
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
        for(int i=0; i<reservation.parking.number_of_places; i++)
        {
            Assert.assertEquals(reservation.wereReservations(i),false);
        }
    }

    @Test
    public void wereReservationWWithReservationsBefore()
    {
        for(int i = 0; i <reservation.parking.number_of_places; i++ )
        {
            reservation.reservPlace(i,new Time(15,40,18,15));
            Assert.assertEquals(reservation.wereReservations(i),true);
        }
    }

    @Test
    public void wereReservationWWithNoReservationsBeforeButNotEmptyParking()
    {
        for(int i = 0; i <reservation.parking.number_of_places -1; i++ )
        {
            reservation.reservPlace(i,new Time(15,40,18,15));
            Assert.assertEquals(reservation.wereReservations(i+1),false);
        }
    }

    @Test
    public void avaliableNotEmptyReservedInPossibletoReserveTime()
    {
        reservation.reservPlace(5,new Time(15,30,17,16));

        Assert.assertEquals(reservation.avaliable(5, new Time (17,40,18,40)),true);
    }

    @Test
    public void avaliableNotEmptyReservedInImpossibleReserveTime()
    {
        reservation.reservPlace(5,new Time(15,30,17,16));

        Assert.assertEquals(reservation.avaliable(5, new Time (17,15,18,40)), false);
    }
}