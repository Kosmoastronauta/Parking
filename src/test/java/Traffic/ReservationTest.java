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
        parking.reset();
    }

    @Test
    public void addSpaceTest() {
        //Given
        //When

        reservation.reservSpace(1,tempTime);
        //Then
        Assert.assertTrue(reservation.occupied_spaces == 1);
    }

    @Test
    public void reservAndRelease() {
        //Given
        //When
        reservation.reservSpace(1,tempTime);
        reservation.release(1, tempTime);
        //Then
        Assert.assertEquals(reservation.spaces[1], false);
    }

    @Test
    public void reservReservedInRage() {
        //Given
        //When
        //   Mockito.when(iparkingFree.inRange(6)).thenReturn(true);
        reservation.reservSpace(3,tempTime);
        //Then
        Assert.assertEquals(reservation.reservSpace(3,tempTime2), 2);
    }

    @Test
    public void reservNotReservedInRange() {
        //Given
        //When
        reservation.reservSpace(6,tempTime);
        // Mockito.when(iparkingFree.inRange(6)).thenReturn(true);
        //Then
        Assert.assertEquals(reservation.reservSpace(7,tempTime), 1);
    }

    @Test
    public void reservNotInRangeLow() {
        //Given
        //When
        // Mockito.when(iparkingFree.inRange(6)).thenReturn(false);

        Assert.assertEquals(reservation.reservSpace(-4,tempTime), 0);
    }

    @Test
    public void releaseFreeInRange() {
        //Given
        //When
        reservation.reservSpace(6,tempTime);
        //Then
        Assert.assertEquals(reservation.release(7, tempTime), 2);
    }

    @Test
    public void releaseOccupiedInRange() {
        //Given
        //When
        reservation.reservSpace(6,tempTime);
        //   Mockito.when(parking.isFree(6)).thenReturn(false);

        //
        //Then
        Assert.assertEquals(reservation.release(6, tempTime), 1);
    }

    @Test
    public void releaseNotInRangeLow() {
        //Given
        //When
        reservation.reservSpace(6,tempTime);
        //Then
        Assert.assertEquals(reservation.release(-6,tempTime), 0);
    }

    @Test
    public void releaseNotInRangeHigh() {
        //Given
        //When
        reservation.reservSpace(6, tempTime);
        // Mockito.when(parking.inRange(6)).thenReturn(false);
        //Then
        Assert.assertEquals(reservation.release(42, tempTime), 0);
    }

    @Test
    public void doubleReserv() {
        reservation.reservSpace(1, tempTime);
        Assert.assertEquals(reservation.reservSpace(1, tempTime), 2);
    }

    @Test
    public void reservNotExistingSpace() {
        ;
        Assert.assertEquals(reservation.reservSpace(-123, tempTime),0);
    }


    @Test
    public void hasReservationChangedStatusfalse() {
        //Given
        //When
        reservation.reservSpace(1, tempTime);
        //Then
        Assert.assertEquals(reservation.possibleToReserve(tempTime2, tempTime), false);
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
        reservation.reservSpace(1, tempTime);
        //Then
        Assert.assertEquals(reservation.isFree(1), false);
    }

    @Test
    public void isFreeNotReserved() {
        //Given
        //When
        reservation.reservSpace(1, tempTime);
        //Then
        Assert.assertEquals(reservation.isFree(1), false);
    }


    @Test
    public void allfalseAfterReset() {
        for (int i = 0; i < reservation.parking.number_of_spaces; i++) {
            reservation.reservSpace(i, tempTime);
        }

        reservation.reset();

        for (int i = 0; i < reservation.parking.number_of_spaces; i++) {
            Assert.assertEquals(reservation.isFree(i), true);
        }
    }

    @Test
    public void timeConflict()
    {
        Assert.assertEquals(reservation.possibleToReserve(tempTime,tempTime2), false);
    }

    @Test
    public void wereReservationWithNoReservationBefore()
    {
        for(int i=0; i<reservation.parking.number_of_spaces; i++)
        {
            Assert.assertEquals(reservation.wereReservations(i),false);
        }
    }

    @Test
    public void wereReservationWWithReservationsBefore()
    {
        for(int i = 0; i <reservation.parking.number_of_spaces; i++ )
        {
            reservation.reservSpace(i,new Time(15,40,18,15));
            Assert.assertEquals(reservation.wereReservations(i),true);
        }
    }

    @Test
    public void wereReservationWWithNoReservationsBeforeButNotEmptyParking()
    {
        for(int i = 0; i <reservation.parking.number_of_spaces -1; i++ )
        {
            reservation.reservSpace(i,new Time(15,40,18,15));
            Assert.assertEquals(reservation.wereReservations(i+1),false);
        }
    }









}
