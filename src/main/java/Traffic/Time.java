package Traffic;

import java.security.InvalidParameterException;

public class Time {

    private int hourFrom;
    private int minFrom;
    private int hourTo;
    private int minTo;

    public Time(int hourFrom, int minFrom, int hourTo, int minTo) {

        if(isPeriodValid(hourFrom,minFrom,hourTo,minTo))
        {
            // OK
        }

        else
        {
            throw new InvalidParameterException("Hour or min is not proper value");
        }

        this.hourFrom = hourFrom;
        this.minFrom = minFrom;
        this.hourTo = hourTo;
        this.minTo = minTo;
    }

    public int valueFrom()
    {
        return minFrom + 60 * hourFrom;
    }

    public int valueTo()
    {
        return minTo + 60 * hourTo;
    }

    public int value(int hour, int min )
    {
        if(isHourValid(hour) && isMinValid(min))
        {
            return hour * 60 + min;
        }

        else
            throw new InvalidParameterException("Inproper input");
    }

    protected boolean isPeriodValid(int hourFrom, int minFrom, int hourTo, int minTo)
    {
        if(isHourValid(hourFrom) && isHourValid(hourTo) && isMinValid(minFrom) && isMinValid(minTo))
        {
            if(value(hourFrom,minFrom) <= value(hourTo,minTo))
            {
                return true;
            }
            else
                return false;
        }
        else
            return false;
    }

    protected boolean isHourValid(int hour)
    {
        if(hour >=0 && hour <=23)
            return true;
        else
            return false;
    }

    protected boolean isMinValid(int min)
    {
        if(min >=0 && min <=59)
            return true;
        else
            return false;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o == this)
            return true;

        if(!(o instanceof Time))
            return false;

        Time t =(Time)o;

        if(this.hourFrom == t.hourFrom && this.minFrom == t.minFrom && this.hourTo == t.hourTo && this.minTo == t.minTo)
            return true;

        else
            return false;
    }
}