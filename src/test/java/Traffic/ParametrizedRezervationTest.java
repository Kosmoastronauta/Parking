package Traffic;

import org.junit.*;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class ParametrizedRezervationTest
{
    private static Reservation reservation;
    private static Parking parking;
    private static Time tempTime;
    private static Time tempTime2;
    private static int currentHourFrom;
    private static int currentMinFrom;
    private static int currentHourTo;
    private static int currentMinTo;

    private ParametrizedRezervationTest() {}

    @After
    public void clear()
    {
        reservation.resetParking(0);
    }

    public ParametrizedRezervationTest(int hourFrom, int minFrom, int hourTo, int minTo)
    {
        currentHourFrom = hourFrom;
        currentMinFrom = minFrom;
        currentHourTo = hourTo;
        currentMinTo = minTo;
    }

    @Before
    public void setUp()
    {
        Parking.idGenerator = 0;
        parking = new Parking(10, "Funny Parking");
        reservation = new Reservation(parking);
      //  reservation = Mockito.spy(reservation);
    }

    @Test
    public void oneHourAndOneMinuteOneHourReservationSamePlace()
    {
        Assert.assertEquals(reservation.reservePlace(0, 1, new Time(currentHourFrom, currentMinFrom, currentHourTo, currentMinTo)), 1);
    }

    @Test
    public void reservationAndReleaseOneMinutOneHourReservation()
    {
        //Given
        //When
        reservation.reservePlace(0, 1, new Time(currentHourFrom, currentMinFrom, currentHourTo, currentMinTo));
        reservation.release(0, 1, new Time(currentHourFrom, currentMinFrom, currentHourTo, currentMinTo));
        //Then
        Assert.assertTrue(reservation.isFreePlace(0, 1));
    }

    @Test
    public void isEmptyParkingAvaliablePlace()
    {
        //Given Empty Parking
        //When Every Place is free
        //Then
        Assert.assertTrue(reservation.isAvaliablePlaceInTime(0, 1, new Time(currentHourFrom, currentMinFrom, currentHourTo, currentMinTo)));
    }

    @Test
    public void notEmptyParkingAvaliableAnotherEmptyPlace()
    {
        //Given Empty Parking
        //When
        reservation.reservePlace(0, 1, new Time(currentHourFrom, currentMinFrom, currentHourTo, currentMinTo));
        //Then
        Assert.assertTrue(reservation.isAvaliablePlaceInTime(0, 2, new Time(currentHourFrom, currentMinFrom, currentHourTo, currentMinTo)));
    }

    @Test
    public void resetTest()
    {
        for(int i = 0; i < reservation.parkings.get(0).numberOfPlaces; i++)
        {
            reservation.reservePlace(0, i, new Time(currentHourFrom, currentMinFrom, currentHourTo, currentMinTo));
            reservation.resetParking(0);
            Assert.assertTrue(reservation.isFreePlace(0, i));
        }
    }

    @Parameterized.Parameters
    public static Collection inputData()
    {
        return Arrays.asList(new Object[][]{{0, 0, 1, 59}, {1, 59, 2, 0}, {2, 0, 3, 59}, {3, 59, 4, 0}, {4, 0, 5, 59}, {5, 59, 6, 0}, {6, 0, 7, 59}, {7, 59, 8, 0}, {8, 0, 9, 59}, {9, 59, 10, 0}, {10, 0, 11, 59}, {11, 59, 12, 0}, {12, 0, 13, 59}, {13, 59, 14, 0}, {14, 0, 15, 59}, {15, 59, 16, 0}, {16, 0, 17, 59}, {17, 59, 18, 0}, {18, 0, 19, 59}, {19, 59, 20, 0}, {20, 0, 21, 59}, {21, 59, 22, 0}, {22, 0, 23, 59}});
    }
}