package Traffic;
import java.util.Arrays;

public class Parking
{
    protected int numberOfPlaces;
    protected int occupiedPlaces;
    protected boolean[] places;
    private int priceDayTariff;
    private int priceNightTariff;

    IparkingFree iparkingFree;

    public Parking(int numberOfPlaces)
    {
        this.numberOfPlaces = numberOfPlaces;
        occupiedPlaces = 0;
        places = new boolean[numberOfPlaces];
        Arrays.fill(places,false);
    }

    public Parking(IparkingFree iparkingFree)
    {
        this.iparkingFree = iparkingFree;
    }

    public String toString()
    {
        String out = "Parking Name: Default Name " + "\n"
                + " places: " + this.occupiedPlaces + "/" + this.numberOfPlaces;

        for(int i = 0; i <this.numberOfPlaces; i++)
        {
            if(this.places[i])
                System.out.println(i + " [Occupied]");

            else
                System.out.println(i + " [Free]");
        }
        return out;
    }

    public int reservPlace(int number_of_place)
    {

        if(this.inRange(number_of_place)) { // Does it exist ?
            if(this.isReserved(number_of_place)) // If it is occupied
            {
                return 2;
            }
            else { // Reserving
                this.occupiedPlaces += 1;
                this.places[number_of_place] = true;
            }


            return 1;
        }
        else
        {
            throw new IllegalArgumentException("Invalid number of place");
            //return 0;
        }
    }

    public boolean inRange(int number_of_place)
    {
        if(number_of_place < this.numberOfPlaces && number_of_place >= 0)
        {
            return true;
        }

        else
            return false;
    }


    public boolean isReserved(int number_of_place)
    {
        if(this.places[number_of_place])
        {
            return true;
        }

        else
            return false;
    }

    public int release(int number_of_place)
    {

        if (this.inRange(number_of_place)) { // Does exist that place ?
            if (this.isFree(number_of_place))
            { // Maybe it is already free
                return 2;
            }

            else //Releasing
                {
                this.occupiedPlaces -= 1;
                this.places[number_of_place] = false;
                return 1;
                }
        }
        else
        {
            return 0;
        }
    }

    public boolean isFree(int number_of_place)
    {
        if(this.places[number_of_place])
        {
            return false;
        }

        else
            return true;
    }

    public void reset()
    {
        for(int i = 0; i<this.numberOfPlaces; i++)
        {
            this.places[i] = false;
        }
        this.occupiedPlaces = 0;
    }

    public boolean empty()
    {
        if(this.occupiedPlaces == 0)
            return true;
        else
            return false;
    }
}