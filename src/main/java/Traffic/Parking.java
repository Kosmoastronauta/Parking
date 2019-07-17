package Traffic;
import java.util.Arrays;

public class Parking
{
    protected int number_of_spaces;
    protected int occupied_spaces;
    protected boolean[] spaces;
    private int priceDayTariff;
    private int priceNightTariff;

    IparkingFree iparkingFree;

    public Parking(int number_of_spaces)
    {
        this.number_of_spaces = number_of_spaces;
        occupied_spaces = 0;
        spaces = new boolean[number_of_spaces];
        Arrays.fill(spaces,false);
    }

    public Parking(IparkingFree iparkingFree)
    {
        this.iparkingFree = iparkingFree;
    }



    public String toString()
    {
        String out = "Parking Name: Default Name " + "\n"
                + " Spaces: " + this.occupied_spaces + "/" + this.number_of_spaces;

        for(int i = 0; i <this.number_of_spaces; i++)
        {
            if(this.spaces[i])
                System.out.println(i + " [Occupied]");

            else
                System.out.println(i + " [Free]");
        }
        return out;
    }

    public int reservSpace(int number_of_space)
    {

        if(this.inRange(number_of_space)) { // Does it exist ?
            if(this.isReserved(number_of_space)) // If it is occupied
            {
                return 2;
            }
            else { // Reserving
                this.occupied_spaces += 1;
                this.spaces[number_of_space] = true;
            }


            return 1;
        }
        else
        {
            throw new IllegalArgumentException("Invalid number of space");
            //return 0;
        }
    }

    public boolean inRange(int number_of_space)
    {
        if(number_of_space < this.number_of_spaces && number_of_space >= 0)
        {
            return true;
        }

        else
            return false;
    }


    public boolean isReserved(int number_of_space)
    {
        if(this.spaces[number_of_space])
        {
            return true;
        }

        else
            return false;
    }

    public int release(int number_of_space)
    {

        if (this.inRange(number_of_space)) { // Does exist that space ?
            if (this.isFree(number_of_space))
            { // Maybe it is already free
                return 2;
            }

            else //Releasing
                {
                this.occupied_spaces -= 1;
                this.spaces[number_of_space] = false;
                return 1;
                }
        }
        else
        {
            return 0;
        }
    }

    public boolean isFree(int number_of_space)
    {
        if(this.spaces[number_of_space])
        {
            return false;
        }

        else
            return true;
    }

    public void reset()
    {
        for(int i=0; i<this.number_of_spaces; i++)
        {
            this.spaces[i] = false;
        }
        this.occupied_spaces = 0;
    }

    public boolean empty()
    {
        if(this.occupied_spaces == 0)
            return true;
        else
            return false;
    }
}
