package Traffic;

import java.security.InvalidParameterException;

public class Time {

    private int hourFrom;
    private int minFrom;
    private int hourTo;
    private int minTo;

    public Time(int hourFrom, int minFrom, int hourTo, int minTo) {

        if(validationHour(hourFrom) && validationHour(hourTo) && validationMin(minFrom) && validationMin(minTo))
        {
            // OK
        }

        else
        {
            throw new InvalidParameterException("Hour or min is not porper value");
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

    protected boolean validationHour(int hour)
    {
        if(hour >=0 && hour <=23)
            return true;
        else
            return false;
    }

    protected boolean validationMin(int min)
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
