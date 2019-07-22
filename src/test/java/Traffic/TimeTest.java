package Traffic;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.lang.reflect.Array;
import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class TimeTest
{

    private int hourFrom;
    private int minFrom;
    private int hourTo;
    private int minTo;
    private static Time tempTime;

    private TimeTest() {}

    public TimeTest(int hourFrom, int minFrom, int hourTo, int minTo, boolean status)
    {
        this.hourFrom = hourFrom;
        this.minFrom = minFrom;
        this.hourTo = hourTo;
        this.minTo = minTo;
    }

    @Before
    public void setUp() {}

    @Test
    public void ValidationTimeNoException()
    {
        Time tempTime = new Time(hourFrom, minFrom, hourTo, minTo);
    }

    @Test(expected = InvalidParameterException.class)
    public void ValidationWithException()
    {
        if(hourFrom != hourTo && minFrom != minTo) { tempTime = new Time(hourTo, minTo, hourFrom, minFrom); }
        else  throw new InvalidParameterException();
    }

    @Parameterized.Parameters
    public static Collection inputData()
    {
        return Arrays.asList(new Object[][]{{14, 20, 15, 10, true}, {0, 0, 0, 59, true}, {12, 0, 12, 0, true}, {15, 2, 17, 40, true}, {0, 0, 23, 59, true}});
    }
}