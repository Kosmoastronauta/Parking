package Traffic;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class ReservationAvaliableParametrizedTest {

    private static Reservation reservation;
    private static Parking parking;
    private static Time tempTime;
    private static Time tempTime2;
    private static int currentHourFrom1;
    private static int currentMinFrom1;
    private static int currentHourTo1;
    private static int currentMinTo1;
    private static int currentHourFrom2;
    private static int currentMinFrom2;
    private static int currentHourTo2;
    private static int currentMinTo2;
    private static boolean currentStatus;

    private ReservationAvaliableParametrizedTest()
    {

    }

    @Before
    public void setUp() {
        parking = new Parking(10);
        reservation = new Reservation(parking);
        reservation.resetParking();
    }

    @After
    public void clear()
    {
        reservation.resetParking();
    }

    public ReservationAvaliableParametrizedTest(int hourFrom1, int minFrom1, int hourTo1, int minTo1, int hourFrom2, int minFrom2, int hourTo2, int minTo2, boolean currentStatus)
    {
        this.currentHourFrom1 = hourFrom1;
        this.currentMinFrom1 = minFrom1;
        this.currentHourTo1 = hourTo1;
        this.currentMinTo1 = minTo1;
        this.currentHourFrom2 = hourFrom2;
        this.currentMinFrom2 = minFrom2;
        this.currentHourTo2 = hourTo2;
        this.currentMinTo2 = minTo2;
        this.currentStatus = currentStatus;

    }

    @Test
    public void isEmptyParkingisAvaliableSpace()
    {
        //Given Empty Parking
        //When Every space is free
        //Then
        Assert.assertEquals(reservation.isAvaliable(1, new Time(currentHourFrom1,currentMinFrom1,currentHourTo1,currentMinTo1)),true);

    }

    @Test
    public void sameSpaceFewReservation()
    {
        //Given Empty Parking
        //When
        reservation.reservePlace(1, new Time(currentHourFrom1,currentMinFrom1,currentHourTo1,currentMinTo1));
        //Then
        Assert.assertEquals(reservation.isAvaliable(1,new Time(currentHourFrom2,currentMinFrom2,currentHourTo2,currentMinTo2)),currentStatus);
    }

    @Test
    public void differentSpaceReservationSameTime()
    {
        //Given Empty Parking
        //When
        reservation.reservePlace(1, new Time(currentHourFrom1,currentMinFrom1,currentHourTo1,currentMinTo1));
        //Then
        Assert.assertEquals(reservation.isAvaliable(2,new Time(currentHourFrom2,currentMinFrom2,currentHourTo2,currentMinTo2)),true);
    }

    @Parameterized.Parameters
    public static Collection inputData()
    {
        return Arrays.asList(new Object[][]
                {{1  ,10	,12	,15	    ,12	,15,    13,	45,	true},
                {15  ,23	,16	,57	    ,11	,51,    17,	30,	false},
                {9   ,30	,10	,15	    ,10	,0,	    10,	15,	false},
                {20  ,15	,21	,30	    ,19	,54,    21,	0,	false}});
    }
}